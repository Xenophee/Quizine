package com.dassonville.api.service;


import com.dassonville.api.dto.*;
import com.dassonville.api.exception.*;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    private final QuizRepository quizRepository;
    private final QuizService quizService;

    private final AnswerRepository answerRepository;



    /**
     * Vérifie si les réponses soumises pour une question sont correctes (par choix).
     *
     * <p>Cette méthode valide que la question appartient au quiz, que les réponses soumises
     * appartiennent bien à la question, et compare les réponses soumises avec les bonnes réponses.</p>
     *
     * @param quizId l'identifiant du quiz
     * @param questionId l'identifiant de la question
     * @param submittedAnswerIds les identifiants des réponses soumises
     * @return un {@link CheckAnswerResultDTO} contenant le résultat de la vérification
     * @throws NotFoundException si la question n'existe pas ou n'appartient pas au quiz
     * @throws InvalidArgumentException si des réponses soumises ne correspondent pas à la question
     * @throws InvalidStateException si la question n'a pas de bonne réponse
     */
    public CheckAnswerResultDTO checkAnswerByChoice(long quizId, long questionId, List<Long> submittedAnswerIds) {
        // Vérifie que la question existe et appartient au bon quiz
        if (!questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId)) {
            logger.warn("La question avec l'ID {}, n'appartient pas au quiz avec l'ID {}.", questionId, quizId);
            throw new NotFoundException(ErrorCode.QUESTION_AND_QUIZ_MISMATCH, questionId, quizId);
        }

        // Vérifie que les réponses soumises appartiennent bien à la question
        long validCount = answerRepository.countActiveValidAnswers(submittedAnswerIds, questionId);
        if (validCount != submittedAnswerIds.size()) {
            logger.warn("Une ou plusieurs réponses soumises ne correspondent pas à la question.");
            throw new InvalidArgumentException(ErrorCode.ANSWERS_AND_QUESTION_MISMATCH, submittedAnswerIds, questionId);
        }

        // Récupère toutes les bonnes réponses
        List<Answer> correctAnswers = answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);

        if (correctAnswers.isEmpty()) {
            logger.error("La question {} n’a pas de bonne réponse !", questionId);
            throw new InvalidStateException(ErrorCode.INTERNAL_ERROR);
        }

        // Vérifie si les réponses soumises sont correctes
        Set<Long> correctIds = correctAnswers.stream().map(Answer::getId).collect(Collectors.toSet());
        logger.info("Les bonnes réponses pour la question {} sont : {}", questionId, correctIds);
        boolean isCorrect = new HashSet<>(submittedAnswerIds).equals(correctIds);

        // Mapping des bonnes réponses à retourner
        List<AnswerForPlayDTO> correctDTOList = correctAnswers.stream()
                .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                .toList();

        return new CheckAnswerResultDTO(isCorrect, correctDTOList);
    }


    /**
     * Vérifie si les réponses soumises pour une question sont correctes (par texte).
     *
     * <p>Cette méthode valide que la question appartient au quiz, normalise les réponses
     * soumises et les compare avec les bonnes réponses normalisées.</p>
     *
     * @param quizId l'identifiant du quiz
     * @param questionId l'identifiant de la question
     * @param submittedAnswers les réponses soumises sous forme de texte
     * @return un {@link CheckAnswerResultDTO} contenant le résultat de la vérification
     * @throws NotFoundException si la question n'existe pas ou n'appartient pas au quiz
     * @throws IllegalStateException si la question n'a pas de bonne réponse
     */
    public CheckAnswerResultDTO checkAnswerByText(long quizId, long questionId, List<String> submittedAnswers) {
        if (!questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId)) {
            logger.warn("La question avec l'ID {}, n'existe pas ou n'appartient pas au quiz avec l'ID {} ou est désactivée.", questionId, quizId);
            throw new NotFoundException(ErrorCode.QUESTION_AND_QUIZ_MISMATCH, questionId, quizId);
        }

        // Récupère les bonnes réponses
        List<Answer> correctAnswers = answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        if (correctAnswers.isEmpty()) {
            logger.error("La question {} n’a pas de bonne réponse !", questionId);
            throw new IllegalStateException("La question ne contient pas de bonne réponse.");
        }
        logger.debug("Réponses correctes pour la question {} : {}", questionId, correctAnswers.stream().map(Answer::getText).collect(Collectors.toList()));

        // Normalisation des réponses correctes
        Set<String> correctNormalized = correctAnswers.stream()
                .map(Answer::getText)
                .map(TextUtils::normalizeAndRemoveAccents)
                .collect(Collectors.toSet());
        logger.debug("Réponses correctes normalisées : {}", correctNormalized);

        // Normalisation des réponses utilisateur
        Set<String> submittedNormalized = submittedAnswers.stream()
                .map(TextUtils::normalizeAndRemoveAccents)
                .collect(Collectors.toSet());
        logger.debug("Réponses soumises normalisées : {}", submittedNormalized);

        // Vérifie que toutes les bonnes réponses sont données, sans extra
        boolean isCorrect = submittedNormalized.equals(correctNormalized);

        // Mapping des bonnes réponses à retourner
        List<AnswerForPlayDTO> correctDTOList = correctAnswers.stream()
                .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                .toList();

        return new CheckAnswerResultDTO(isCorrect, correctDTOList);
    }


    /**
     * Crée une nouvelle question pour un quiz donné.
     *
     * <p>Cette méthode vérifie si le quiz existe, normalise le texte de la question,
     * vérifie qu'aucune question avec le même texte n'existe déjà pour le quiz, puis
     * crée et sauvegarde la nouvelle question dans la base de données.</p>
     *
     * @param quizId l'identifiant du quiz auquel la question sera associée
     * @param dto les informations de la question à créer contenues dans un {@link QuestionInsertDTO}
     * @return un {@link QuestionAdminDTO} contenant les détails de la question créée
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si une question avec le même texte existe déjà pour le quiz
     */
    @Transactional
    public QuestionAdminDTO create(long quizId, QuestionInsertDTO dto) {

        if (!quizRepository.existsById(quizId)) {
            logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", quizId);
            throw new NotFoundException(ErrorCode.QUIZ_NOT_FOUND, quizId);
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizIdAndTextIgnoreCase(quizId, normalizedNewText)) {
            logger.warn("La question {}, existe déjà pour ce quiz.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUESTION_ALREADY_EXISTS, normalizedNewText);
        }

        Question questionToCreate = questionMapper.toModel(dto, quizId);

        Question questionCreated = questionRepository.save(questionToCreate);

        return questionMapper.toAdminDTO(questionCreated);
    }


    /**
     * Met à jour une question existante avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche la question par son ID, normalise le nouveau texte,
     * vérifie qu'aucune autre question avec le même texte n'existe déjà pour le quiz,
     * puis met à jour et sauvegarde la question dans la base de données.</p>
     *
     * @param id l'identifiant de la question à mettre à jour
     * @param dto les nouvelles informations de la question contenues dans un {@link QuestionUpdateDTO}
     * @return un {@link QuestionAdminDTO} contenant les détails de la question mise à jour
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si une autre question avec le même texte existe déjà pour le quiz
     */
    public QuestionAdminDTO update(long id, QuestionUpdateDTO dto) {

        Question questionToUpdate = findQuestionById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizIdAndTextIgnoreCaseAndIdNot(questionToUpdate.getQuiz().getId(), normalizedNewText, id)) {
            logger.warn("La question {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUESTION_ALREADY_EXISTS, normalizedNewText);
        }

        questionMapper.updateModelFromDTO(dto, questionToUpdate);

        Question updatedQuestion = questionRepository.save(questionToUpdate);

        return questionMapper.toAdminDTO(updatedQuestion);
    }


    /**
     * Supprime une question par son identifiant.
     *
     * <p>Cette méthode recherche la question par son ID, la supprime de la base de données,
     * puis désactive le quiz associé si le nombre de questions actives devient insuffisant.</p>
     *
     * @param id l'identifiant de la question à supprimer
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     */
    public void delete(long id) {

        Question question = findQuestionById(id);

        questionRepository.deleteById(id);

        disableQuizIfTooFewActiveQuestions(question.getQuiz().getId());
    }


    /**
     * Met à jour la visibilité d'une question.
     *
     * <p>Cette méthode active ou désactive une question en fonction de l'état de visibilité fourni.
     * Si la question est désactivée, elle vérifie si le quiz associé doit également être désactivé faute de questions actives suffisantes.</p>
     *
     * @param id l'identifiant de la question à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     */
    public void updateVisibility(long id, boolean visible) {

        Question question = findQuestionById(id);

        if (question.isVisible() == visible) return;

        question.setVisible(visible);

        questionRepository.save(question);

        disableQuizIfTooFewActiveQuestions(question.getQuiz().getId());
    }


    /**
     * Recherche une question par son identifiant.
     *
     * <p>Cette méthode tente de trouver une question par son identifiant.
     * Si la question n'est pas trouvée, elle lève une exception.</p>
     *
     * @param id l'identifiant de la question à rechercher
     * @return la {@link Question} trouvée
     * @throws NotFoundException si aucune question ne correspond à l'identifiant fourni
     */
    private Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException(ErrorCode.QUESTION_NOT_FOUND, id);
                });
    }


    /**
     * Désactive un quiz si le nombre de questions actives devient insuffisant.
     *
     * <p>Cette méthode compte le nombre de questions actives associées à un quiz.
     * Si ce nombre est inférieur au minimum requis, elle désactive le quiz.</p>
     *
     * @param quizId l'identifiant du quiz à vérifier
     */
    private void disableQuizIfTooFewActiveQuestions(long quizId) {

        int numberOfActiveQuestions = questionRepository.countByQuizIdAndDisabledAtIsNull(quizId);

        if (numberOfActiveQuestions < MINIMUM_QUIZ_QUESTIONS) {
            logger.warn("Le quiz avec l'ID {}, n'a pas suffisamment de questions : {}, il a donc été désactivé.", quizId, numberOfActiveQuestions);
            quizService.updateVisibility(quizId, false);
        } else {
            logger.debug("Le quiz avec l'ID {}, a suffisamment de questions : {}, il reste actif.", quizId, numberOfActiveQuestions);
        }
    }
    
}
