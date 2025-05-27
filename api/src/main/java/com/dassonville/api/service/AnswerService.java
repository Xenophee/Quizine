package com.dassonville.api.service;


import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    private final QuestionRepository questionRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;


    /**
     * Crée une nouvelle réponse pour une question donnée.
     *
     * <p>Cette méthode vérifie si la question existe, normalise le texte de la réponse,
     * vérifie qu'aucune réponse avec le même texte n'existe déjà pour la question, puis
     * crée et sauvegarde la nouvelle réponse dans la base de données.</p>
     *
     * @param questionId l'identifiant de la question à laquelle la réponse sera associée
     * @param dto        les informations de la réponse à créer contenues dans un {@link AnswerUpsertDTO}
     * @return un {@link AnswerAdminDTO} contenant les détails de la réponse créée
     * @throws NotFoundException     si la question avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si une réponse avec le même texte existe déjà pour la question
     */
    public AnswerAdminDTO create(long questionId, AnswerUpsertDTO dto) {

        if (!questionRepository.existsById(questionId)) {
            logger.warn("La question avec l'ID {}, n'a pas été trouvée.", questionId);
            throw new NotFoundException("La question n'a pas été trouvée.");
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (answerRepository.existsByTextIgnoreCaseAndQuestionId(normalizedNewText, questionId)) {
            logger.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        Answer answerToCreate = answerMapper.toModel(dto, questionId);

        Answer answerCreated = answerRepository.save(answerToCreate);

        return answerMapper.toAdminDTO(answerCreated);
    }


    /**
     * Met à jour une réponse existante avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche la réponse par son ID, normalise le nouveau texte,
     * vérifie qu'aucune autre réponse avec le même texte n'existe déjà pour la question concernée, puis met à jour
     * et sauvegarde la réponse dans la base de données.</p>
     *
     * <p>Elle vérifie également les contraintes liées aux réponses correctes.
     * Si la réponse à mettre à jour est initialement correcte et que la nouvelle réponse ne l'est pas,
     * elle s'assure qu'il existe au moins une autre réponse correcte active pour la question.</p>
     *
     * @param id  l'identifiant de la réponse à mettre à jour
     * @param dto les nouvelles informations de la réponse contenues dans un {@link AnswerUpsertDTO}
     * @return un {@link AnswerAdminDTO} contenant les détails de la réponse mise à jour
     * @throws NotFoundException         si la réponse avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException     si une autre réponse avec le même texte existe déjà
     * @throws ActionNotAllowedException si les contraintes sur les réponses correctes ne sont pas respectées
     */
    public AnswerAdminDTO update(long id, AnswerUpsertDTO dto) {

        Answer answerToUpdate = findAnswerById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (answerRepository.existsByTextIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        if (answerToUpdate.getIsCorrect() && !dto.isCorrect()) {
            logger.debug("La réponse concernée est initialement correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answerToUpdate.getQuestion().getId(), answerToUpdate.getId());
        }

        answerMapper.updateModelFromDTO(dto, answerToUpdate);

        Answer updatedAnswer = answerRepository.save(answerToUpdate);

        return answerMapper.toAdminDTO(updatedAnswer);
    }


    /**
     * Supprime une réponse par son identifiant.
     *
     * <p>Cette méthode vérifie si la réponse existe, valide les contraintes sur le nombre minimum
     * de réponses actives et les réponses correctes, puis supprime la réponse de la base de données.</p>
     *
     * @param id l'identifiant de la réponse à supprimer
     * @throws NotFoundException si la réponse avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si les contraintes sur les réponses actives ou correctes ne sont pas respectées
     */
    public void delete(long id) {

        Answer answer = findAnswerById(id);

        validateMinimumActiveAnswers(answer.getQuestion().getId());

        if (answer.getIsCorrect()) {
            logger.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answerRepository.deleteById(id);
    }


    /**
     * Met à jour la visibilité d'une réponse.
     *
     * <p>Cette méthode active ou désactive une réponse en fonction de l'état de visibilité fourni.
     * Elle vérifie également les contraintes sur le nombre minimum de réponses actives et les réponses correctes.</p>
     *
     * @param id l'identifiant de la réponse à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si la réponse avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si les contraintes sur les réponses actives ou correctes ne sont pas respectées
     */
    public void updateVisibility(long id, boolean visible) {

        Answer answer = findAnswerById(id);

        if (answer.isVisible() == visible) return;

        if (!visible) {
            logger.debug("Désactivation demandée de la réponse avec l'ID {}, vérification du nombre de réponses actives restantes.", id);
            validateMinimumActiveAnswers(answer.getQuestion().getId());
        }

        if (answer.getIsCorrect()) {
            logger.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answer.setVisible(visible);

        answerRepository.save(answer);
    }



    /**
     * Recherche une réponse par son identifiant.
     *
     * <p>Cette méthode tente de trouver une réponse par son identifiant.
     * Si la réponse n'est pas trouvée, elle lève une exception.</p>
     *
     * @param id l'identifiant de la réponse à rechercher
     * @return la {@link Answer} trouvée
     * @throws NotFoundException si aucune réponse ne correspond à l'identifiant fourni
     */
    private Answer findAnswerById(long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La réponse n'a pas été trouvée.");
                });
    }

    /**
     * Valide qu'il existe au moins une réponse correcte active pour une question donnée.
     *
     * <p>Cette méthode vérifie si une autre réponse correcte active existe pour la question,
     * en excluant une réponse spécifique. Si aucune réponse correcte active n'est trouvée,
     * une exception est levée.</p>
     *
     * @param questionId l'identifiant de la question
     * @param excludedAnswerId l'identifiant de la réponse à exclure de la vérification
     * @throws ActionNotAllowedException si aucune autre réponse correcte active n'est trouvée
     */
    private void validateActiveCorrectAnswerExists(long questionId, long excludedAnswerId) {

        boolean hasCorrectAnswer = answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(questionId, excludedAnswerId);

        if (!hasCorrectAnswer) {
            logger.warn("Il doit y avoir au moins une réponse correcte active pour la question avec l'ID {}.", questionId);
            throw new ActionNotAllowedException("Il doit y avoir au moins une réponse correcte active pour cette question.");
        }
    }

    /**
     * Valide qu'une question contient au moins un nombre minimum de réponses actives.
     *
     * <p>Cette méthode compte le nombre de réponses actives associées à une question.
     * Si ce nombre est inférieur au minimum requis, elle lève une exception.</p>
     *
     * @param questionId l'identifiant de la question à vérifier
     * @throws ActionNotAllowedException si le nombre de réponses actives est insuffisant
     */
    private void validateMinimumActiveAnswers(long questionId) {

        int numberOfActiveAnswers = answerRepository.countByQuestionIdAndDisabledAtIsNull(questionId) - 1;
        logger.debug("Nombre de réponses actives restantes pour la question {} après l'opération : {}", questionId, numberOfActiveAnswers);

        byte minimumNumberOfAnswers = difficultyLevelRepository.findAnswerOptionsCountByReferenceLevel()
                .orElseThrow(() -> {
                    logger.error("Le niveau de difficulté de référence n'a pas été trouvé.");
                    return new IllegalStateException("Le niveau de difficulté de référence n'a pas été trouvé.");
                });

        if (numberOfActiveAnswers < minimumNumberOfAnswers) {
            logger.warn("Il doit y avoir au moins {} réponses actives pour cette question.", minimumNumberOfAnswers);
            throw new ActionNotAllowedException("Il doit y avoir au moins " + minimumNumberOfAnswers + " réponses actives pour cette question.");
        }
    }

}
