package com.dassonville.api.service;


import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.exception.*;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.model.Question;
import com.dassonville.api.repository.ClassicAnswerRepository;
import com.dassonville.api.repository.GameRuleRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final ClassicAnswerRepository classicAnswerRepository;
    private final AnswerMapper answerMapper;

    private final QuestionRepository questionRepository;
    private final GameRuleRepository gameRuleRepository;


    /**
     * Crée une nouvelle réponse pour une question donnée.
     *
     * <p>Cette méthode vérifie si la question existe, normalise le texte de la réponse,
     * vérifie qu'aucune réponse avec le même texte n'existe déjà pour la question, puis
     * crée et sauvegarde la nouvelle réponse dans la base de données.</p>
     *
     * @param questionId l'identifiant de la question à laquelle la réponse sera associée
     * @param dto        les informations de la réponse à créer contenues dans un {@link ClassicAnswerUpsertDTO}
     * @return un {@link AnswerAdminDTO} contenant les détails de la réponse créée
     * @throws NotFoundException     si la question avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si la question n'autorise pas le type de réponse envoyé
     * @throws AlreadyExistException si une réponse avec le même texte existe déjà pour la question
     */
    public AnswerAdminDTO create(long questionId, ClassicAnswerUpsertDTO dto) {

        if (!questionRepository.existsById(questionId)) {
            log.warn("La question avec l'ID {}, n'a pas été trouvée.", questionId);
            throw new NotFoundException(ErrorCode.QUESTION_NOT_FOUND);
        }

        if (!questionRepository.existsByIdAndQuestionTypeCode(questionId, AppConstants.CLASSIC_QUESTION_TYPE)) {
            log.warn("La question avec l'ID {}, n'autorise pas le type d'enregistrement {}.", questionId, AppConstants.CLASSIC_QUESTION_TYPE);
            throw new ActionNotAllowedException(ErrorCode.ANSWER_TYPE_NOT_SUPPORTED);
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (classicAnswerRepository.existsByTextIgnoreCaseAndQuestionId(normalizedNewText, questionId)) {
            log.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.ANSWER_ALREADY_EXISTS);
        }

        ClassicAnswer classicAnswerToCreate = answerMapper.toModel(dto, questionId);

        ClassicAnswer classicAnswerCreated = classicAnswerRepository.save(classicAnswerToCreate);

        return answerMapper.toAdminDTO(classicAnswerCreated);
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
     * @param dto les nouvelles informations de la réponse contenues dans un {@link ClassicAnswerUpsertDTO}
     * @return un {@link AnswerAdminDTO} contenant les détails de la réponse mise à jour
     * @throws NotFoundException         si la réponse avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException     si une autre réponse avec le même texte existe déjà
     * @throws ActionNotAllowedException si les contraintes sur les réponses correctes ne sont pas respectées
     */
    @Transactional
    public AnswerAdminDTO update(long id, ClassicAnswerUpsertDTO dto) {

        ClassicAnswer answer = findAnswerById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (classicAnswerRepository.existsByTextIgnoreCaseAndIdNot(normalizedNewText, id)) {
            log.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.ANSWER_ALREADY_EXISTS);
        }

        if (answer.getIsCorrect() && !dto.isCorrect()) {
            log.debug("La réponse concernée est initialement correcte, vérification de l'existence d'une autre réponse correcte active.");
            assertAtLeastOneActiveCorrectAnswerRemains(answer.getQuestion().getId(), answer.getId());
        }

        answerMapper.updateModelFromDTO(dto, answer);

        return answerMapper.toAdminDTO(answer);
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

        ClassicAnswer classicAnswer = findAnswerById(id);

        assertMinimumActiveAnswersRemain(classicAnswer.getQuestion());

        if (classicAnswer.getIsCorrect()) {
            log.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            assertAtLeastOneActiveCorrectAnswerRemains(classicAnswer.getQuestion().getId(), classicAnswer.getId());
        }

        classicAnswerRepository.deleteById(id);
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

        ClassicAnswer classicAnswer = findAnswerById(id);

        if (classicAnswer.isVisible() == visible) return;

        if (!visible) {
            log.debug("Désactivation demandée de la réponse avec l'ID {}, vérification du nombre de réponses actives restantes.", id);
            assertMinimumActiveAnswersRemain(classicAnswer.getQuestion());
        }

        if (classicAnswer.getIsCorrect()) {
            log.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            assertAtLeastOneActiveCorrectAnswerRemains(classicAnswer.getQuestion().getId(), classicAnswer.getId());
        }

        classicAnswer.setVisible(visible);

        classicAnswerRepository.save(classicAnswer);
    }



    /**
     * Recherche une réponse par son identifiant.
     *
     * <p>Cette méthode tente de trouver une réponse par son identifiant.
     * Si la réponse n'est pas trouvée, elle lève une exception.</p>
     *
     * @param id l'identifiant de la réponse à rechercher
     * @return la {@link ClassicAnswer} trouvée
     * @throws NotFoundException si aucune réponse ne correspond à l'identifiant fourni
     */
    private ClassicAnswer findAnswerById(long id) {
        return classicAnswerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException(ErrorCode.ANSWER_NOT_FOUND);
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
    private void assertAtLeastOneActiveCorrectAnswerRemains(long questionId, long excludedAnswerId) {

        boolean hasCorrectAnswer = classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(questionId, excludedAnswerId);

        if (!hasCorrectAnswer) {
            log.warn("Il doit y avoir au moins une réponse correcte active pour la question avec l'ID {}.", questionId);
            throw new ActionNotAllowedException(ErrorCode.AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY);
        }
    }

    /**
     * Valide qu'une question contient au moins un nombre minimum de réponses actives.
     *
     * <p>Cette méthode compte le nombre de réponses actives associées à une question.
     * Si ce nombre est inférieur au minimum requis, elle lève une exception.</p>
     *
     * @param question la question à vérifier
     * @throws InvalidStateException si les règles de difficulté de référence n'ont pas été trouvées
     * @throws ActionNotAllowedException si le nombre de réponses actives est insuffisant
     */
    private void assertMinimumActiveAnswersRemain(Question question) {

        int numberOfActiveAnswers = classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(question.getId()) - 1;
        log.debug("Nombre de réponses actives restantes pour la question {} après l'opération : {}", question, numberOfActiveAnswers);

        byte minimumNumberOfAnswers = gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(question.getQuestionType().getCode())
                .orElseThrow(() -> {
                    log.error("Les règles de difficulté de référence n'ont pas été trouvées pour la question avec l'ID {}.", question);
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        if (numberOfActiveAnswers < minimumNumberOfAnswers) {
            log.warn("Il doit y avoir au moins {} réponses actives pour cette question.", minimumNumberOfAnswers);
            throw new ActionNotAllowedException(ErrorCode.MINIMUM_ACTIVE_ANSWERS_NOT_REACHED, minimumNumberOfAnswers);
        }
    }

}
