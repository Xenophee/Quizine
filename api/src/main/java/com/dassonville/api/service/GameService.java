package com.dassonville.api.service;

import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.response.AnswerForPlayDTO;
import com.dassonville.api.dto.response.CheckAnswerResultDTO;
import com.dassonville.api.dto.response.QuestionForPlayDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.exception.MisconfiguredException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.projection.AnswerOptionsRuleProjection;
import com.dassonville.api.projection.QuestionForPlayProjection;
import com.dassonville.api.repository.*;
import com.dassonville.api.service.checker.QuestionAnswerChecker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final List<QuestionAnswerChecker> checkers;

    private final QuizTypeRepository quizTypeRepository;
    private final QuestionRepository questionRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final GameRuleRepository gameRuleRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;


    /**
     * Récupère toutes les questions d'un quiz pour le jeu, en fonction du niveau de difficulté.
     *
     * <p>Cette méthode recherche un quiz par son identifiant et un niveau de difficulté spécifique,
     * puis récupère les questions associées à ce quiz. Les questions sont mélangées et mappées vers
     * des DTOs adaptés pour le jeu, en respectant le nombre maximum de réponses affichées par le niveau
     * de difficulté.</p>
     *
     * @param quizId l'identifiant du quiz
     * @param difficultyLevelId l'identifiant du niveau de difficulté
     * @return une liste de {@link QuestionForPlayDTO} contenant les questions pour le jeu
     * @throws NotFoundException si le quiz ou le niveau de difficulté n'est pas trouvé
     */
    @Transactional
    public List<QuestionForPlayDTO> findAllQuestionsByQuizIdForPlay(long quizId, long difficultyLevelId) {

        // Vérification de l'existence du quiz en récupérant le type de quiz
        String quizTypeCode = quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(quizId)
                .orElseThrow(() -> {
                    log.warn("Le quiz avec l'ID {} n'existe pas ou est désactivé.", quizId);
                    return new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
                });
        log.debug("Type de quiz récupéré pour l'ID {} : {}", quizId, quizTypeCode);

        // Vérification de l'existence du niveau de difficulté
        if (!difficultyLevelRepository.existsById(difficultyLevelId)) {
            log.warn("Le niveau de difficulté avec l'ID {} n'existe pas.", difficultyLevelId);
            throw new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND);
        }

        // Récupération des types de questions associés au type de quiz et récupération des règles d'options de réponse
        List<String> questionTypeCodes = questionTypeRepository.findIdsByQuizTypeCode(quizTypeCode);
        Map<String, AnswerOptionsRuleProjection> rulesByType = loadAnswerRules(questionTypeCodes, difficultyLevelId);


        // Récupération des questions actives du quiz
        List<QuestionForPlayProjection> questions = questionRepository
                .findByQuizzesIdAndDisabledAtIsNullAndClassicAnswersDisabledAtIsNull(quizId);

        // Filtrage des questions par type et niveau de difficulté
        List<QuestionForPlayDTO> questionsForPlay = new ArrayList<>(questions.stream()
                .map(question -> {

                    String questionTypeCode = question.getQuestionType().getCode();

                    // Récupération de toutes les informations nécessaires du quiz d'origine en se basant sur la date de création
                    Optional<QuestionForPlayProjection.QuizShortProjection> quizMeta = question.getQuizzes().stream()
                            .min(Comparator.comparing(QuestionForPlayProjection.QuizShortProjection::getCreatedAt));

                    // Récupération du thème de la question
                    String theme = quizMeta
                            .filter(quiz -> quiz.getTheme() != null)
                            .map(quiz -> quiz.getTheme().getName())
                            .orElseThrow( () -> {
                                log.error("La question avec l'id {} n'a pas de thème.", question.getId());
                                return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                            });

                    // Récupération de la catégorie de la question
                    String category = quizMeta
                            .filter(quiz -> quiz.getCategory() != null)
                            .map(quiz -> quiz.getCategory().getName())
                            .orElse(null);
                    log.debug("La question avec l'id {} a pour thème : {}, et pour catégorie : {}", question.getId(), theme, category);

                    // Récupération du niveau de maîtrise de la question
                    String masteryLevel = quizMeta
                            .filter(quiz -> quiz.getMasteryLevel() != null)
                            .map(quiz -> quiz.getMasteryLevel().getName())
                            .orElseThrow( () -> {
                                log.error("La question avec l'id {} n'a pas de niveau de maîtrise.", question.getId());
                                return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                            });

                    // Création du DTO de question de type TRUE_FALSE
                    if (question.getClassicAnswers().isEmpty()) {
                        log.debug("Traitement de la question vrai/faux avec l'id : {}", question.getId());
                        return new QuestionForPlayDTO(
                                question.getId(),
                                masteryLevel,
                                theme,
                                category,
                                question.getQuestionType().getName(),
                                question.getQuestionType().getInstruction(),
                                question.getText(),
                                List.of());
                    }

                    // Détermination du nombre d'options de réponse à afficher
                    byte answerOptionsCount = resolveAnswerOptionsCount(questionTypeCode, rulesByType);
                    log.debug("Nombre de réponses à indiquer pour la question {} : {}", question.getId(), answerOptionsCount);

                    // Préparation des réponses de la question pour le jeu
                    List<QuestionForPlayProjection.AnswersForPlay> preparedAnswers =
                            prepareAnswersForPlay(question, answerOptionsCount);

                    // Création du DTO des réponses pour la question
                    List<AnswerForPlayDTO> finalAnswers = preparedAnswers.stream()
                            .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                            .collect(Collectors.toList());

                    // Création du DTO de question pour le jeu
                    return new QuestionForPlayDTO(
                            question.getId(),
                            masteryLevel,
                            theme,
                            category,
                            question.getQuestionType().getName(),
                            question.getQuestionType().getInstruction(),
                            question.getText(),
                            finalAnswers);
                })
                .toList());


        // Mélange les questions et les retourne
        Collections.shuffle(questionsForPlay);
        return questionsForPlay;
    }



    /**
     * Charge les règles d'options de réponse pour les types de questions spécifiés et le niveau de difficulté.
     *
     * <p>Cette méthode récupère les règles d'options de réponse en fonction des identifiants des types de questions
     * et du niveau de difficulté. Elle gère les doublons en conservant la première règle rencontrée pour chaque type.</p>
     *
     * @param questionTypeCodes une liste d'identifiants de types de questions
     * @param difficultyLevelId l'identifiant du niveau de difficulté
     * @return une map des règles d'options de réponse par type de question
     * @throws NotFoundException si le niveau de difficulté n'existe pas ou est désactivé pour un type de question unique
     */
    private Map<String, AnswerOptionsRuleProjection> loadAnswerRules(
            List<String> questionTypeCodes,
            long difficultyLevelId
    ) {

        boolean includesSpecial = questionTypeCodes.size() == 1;

        // Pour les types de question uniques, vérification de l'existence du niveau de difficulté précis afin d'éviter les incohérences
        if (includesSpecial) {
            log.debug("Un seul type de question : {}", questionTypeCodes.getFirst());
            boolean isDifficultyExist = questionTypeRepository.existsByIdAndDifficultyLevelsId(questionTypeCodes.getFirst(), difficultyLevelId);

            if (!isDifficultyExist) {
                log.warn("Le niveau de difficulté {} n'existe pas ou est désactivé pour le type de question {}.", difficultyLevelId, questionTypeCodes.getFirst());
                throw new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND);
            }
        }

        List<AnswerOptionsRuleProjection> rules =
                gameRuleRepository.findAnswerOptionsBestMatchingRulesByQuestionTypeCodes(questionTypeCodes, difficultyLevelId, includesSpecial);

        return rules.stream()
                .collect(Collectors.toMap(
                        AnswerOptionsRuleProjection::getQuestionTypeCode,
                        rule -> rule
                ));
    }

    /**
     * Résout le nombre d'options de réponse à afficher pour une question en fonction de son type et de son niveau de difficulté.
     *
     * <p>Cette méthode recherche les règles d'options de réponse pour un type de question donné et
     * détermine le nombre d'options à afficher en fonction du niveau de difficulté.</p>
     *
     * @param questionTypeCode l'identifiant du type de question
     * @param rulesByType une map des règles d'options de réponse par type de question
     * @return le nombre d'options de réponse à afficher
     * @throws InvalidStateException si aucune règle n'est trouvée pour le type de question
     */
    private byte resolveAnswerOptionsCount(
            String questionTypeCode,
            Map<String, AnswerOptionsRuleProjection> rulesByType
    ) {

        AnswerOptionsRuleProjection rule = rulesByType.get(questionTypeCode);

        if (rule == null) {
            log.error("Aucune règle de réponse trouvée pour le questionTypeCode {}", questionTypeCode);
            throw new InvalidStateException(ErrorCode.INTERNAL_ERROR);
        }

        return rule.getAnswerOptionsCount();
    }


    /**
     * Prépare les réponses à afficher pour une question de jeu.
     *
     * <p>Cette méthode sélectionne les réponses correctes et incorrectes d'une question,
     * limite le nombre de réponses correctes en fonction du nombre d'options de réponse
     * spécifié, et mélange les réponses avant de les retourner.</p>
     *
     * @param question la question pour laquelle préparer les réponses
     * @param answerOptionsCount le nombre d'options de réponse à afficher
     * @return une liste de réponses préparées pour l'affichage dans le jeu
     * @throws InvalidStateException si la question n'a pas de réponse correcte
     */
    private List<QuestionForPlayProjection.AnswersForPlay> prepareAnswersForPlay(
            QuestionForPlayProjection question,
            byte answerOptionsCount
    ) {

        List<QuestionForPlayProjection.AnswersForPlay> allAnswers = question.getClassicAnswers();

        // Récupération des réponses correctes
        List<QuestionForPlayProjection.AnswersForPlay> correctAnswers = allAnswers.stream()
                .filter(QuestionForPlayProjection.AnswersForPlay::getIsCorrect)
                .toList();

        if (correctAnswers.isEmpty()) {
            log.error("La question {} n'a pas de réponse correcte.", question.getId());
            throw new InvalidStateException(ErrorCode.INTERNAL_ERROR);
        }

        if (answerOptionsCount == 0) return List.of(); // Réponse libre, pas d’options à afficher

        // Récupération des réponses incorrectes
        List<QuestionForPlayProjection.AnswersForPlay> incorrectAnswers = allAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .toList();

        // Détermination du nombre d'options incorrectes à sélectionner
        int slotsForIncorrects = answerOptionsCount - correctAnswers.size();

        // Limite le nombre de réponses correctes à afficher en fonction du nombre d'options de réponse
        List<QuestionForPlayProjection.AnswersForPlay> limitedCorrects = correctAnswers.size() > answerOptionsCount
                ? correctAnswers.subList(0, answerOptionsCount)
                : correctAnswers;

        // Sélection aléatoire des réponses incorrectes en fonction des slots disponibles
        List<QuestionForPlayProjection.AnswersForPlay> selectedIncorrects = slotsForIncorrects > 0
                ? incorrectAnswers.stream()
                    .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                        Collections.shuffle(list);
                        return list.stream();
                    }))
                    .limit(slotsForIncorrects)
                    .toList()
                : List.of();

        // Combine les réponses correctes et incorrectes
        List<QuestionForPlayProjection.AnswersForPlay> combined = Stream.concat(
                limitedCorrects.stream(),
                selectedIncorrects.stream()
        ).collect(Collectors.toList());

        // Mélange les réponses et les retourne
        Collections.shuffle(combined);
        return combined;
    }


    /**
     * Vérifie la réponse à une question en fonction du type de question.
     *
     * <p>Cette méthode utilise un {@link QuestionAnswerChecker} approprié pour vérifier la réponse
     * en fonction du type de question spécifié dans la requête.</p>
     *
     * @param request la requête contenant l'ID de la question, l'ID du quiz, l'ID de la difficulté, le type de question et les réponses soumises
     * @return un {@link CheckAnswerResultDTO} contenant le résultat de la vérification
     * @throws NotFoundException si la question n'existe pas ou n'appartient pas au quiz ou si le niveau de difficulté n'existe pas ou est désactivé
     * @throws com.dassonville.api.exception.InvalidArgumentException si les réponses données sont hors limites ou invalides
     * @throws InvalidStateException si le type de vérification n'est pas supporté ou s'il manque des données nécessaires en interne
     */
    public CheckAnswerResultDTO checkAnswer(QuestionAnswerRequestDTO request) {

        if (!questionRepository.existsByIdAndDisabledAtIsNullEverywhere(request.questionId(), request.quizId())) {
            log.warn("La question avec l'ID {}, n'appartient pas au quiz avec l'ID {}.", request.questionId(), request.quizId());
            throw new NotFoundException(ErrorCode.QUESTION_AND_QUIZ_MISMATCH);
        }

        // Vérification de l'existence du quiz en récupérant le type de quiz
        String quizTypeCode = quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(request.quizId())
                .orElseThrow(() -> {
                    log.warn("Aucun type de quiz trouvé pour l'ID {}", request.quizId());
                    return new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
                });
        log.debug("Type de quiz récupéré pour l'ID {} : {}", request.quizId(), quizTypeCode);

        // Vérification que le type de quiz correspond à celui attendu dans la requête
        if (!quizTypeCode.equals(request.type().getMainType())) {
            log.warn("Le type de quiz récupéré ({}) ne correspond pas au type attendu ({}) pour la requête.", quizTypeCode, request.type().getMainType());
            throw new MisconfiguredException(ErrorCode.QUIZ_TYPE_MISMATCH);
        }

        // Récupération des types de questions associés au type de quiz
        List<String> questionTypeCodes = questionTypeRepository.findIdsByQuizTypeCode(quizTypeCode);

        // Vérification de la validité du niveau de difficulté
        assertValidDifficultyForQuiz(request, questionTypeCodes);


        boolean isQuizSoloQuestionType = questionTypeCodes.size() == 1;

        return checkers.stream()
                .filter(checker -> checker.supports(request.type()))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Aucun vérificateur disponible pour le type de question : {}", request.type());
                    return new InvalidStateException(ErrorCode.CHECK_ANSWER_TYPE_NOT_SUPPORTED);
                })
                .checkAnswer(request, isQuizSoloQuestionType);
    }



    /**
     * Vérifie si le niveau de difficulté est valide pour le quiz en fonction du type de question.
     *
     * <p>Cette méthode valide le niveau de difficulté en fonction du type de question du quiz.
     * Si le quiz contient un seul type de question, elle vérifie la validité du niveau de difficulté
     * pour ce type. Si le quiz contient plusieurs types de questions, elle vérifie la validité du niveau
     * de difficulté pour l'ensemble du quiz.</p>
     *
     * @param request la requête contenant les informations nécessaires pour la validation
     * @param questionTypeCodes les identifiants des types de questions associés au quiz
     * @throws NotFoundException si le niveau de difficulté n'est pas valide pour le quiz
     */
    private void assertValidDifficultyForQuiz(QuestionAnswerRequestDTO request, List<String> questionTypeCodes) {
        boolean isQuizSoloQuestionType = questionTypeCodes.size() == 1;

        if (isQuizSoloQuestionType) {
            validateDifficultyForSingleQuestionType(questionTypeCodes.getFirst(), request.difficultyId());
        } else {
            validateDifficultyForMultipleQuestionTypes(request.quizId(), request.difficultyId());
        }
    }


    /**
     * Valide si le niveau de difficulté est associé à un type de question unique.
     *
     * <p>
     * Cette méthode vérifie si le niveau de difficulté est valide pour un type de question unique.
     * Sa validité est vérifié avec précision afin de s'assurer de la cohérence.
     * </p>
     *
     * @param questionTypeCode l'identifiant du type de question
     * @param difficultyId l'identifiant du niveau de difficulté
     * @throws NotFoundException si le niveau de difficulté n'existe pas ou est désactivé pour le type de question
     */
    private void validateDifficultyForSingleQuestionType(String questionTypeCode, long difficultyId) {
        log.debug("Validation du niveau de difficulté {} pour un type de question unique : {}", difficultyId, questionTypeCode);

        if (!questionTypeRepository.existsByIdAndDifficultyLevelsId(questionTypeCode, difficultyId)) {
            log.warn("Le niveau de difficulté {} n'existe pas ou est désactivé pour le type de question {}.", difficultyId, questionTypeCode);
            throw new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND);
        }
    }

    /**
     * Valide si le niveau de difficulté est associé à un quiz avec plusieurs types de questions.
     *
     * <p>
     * Cette méthode vérifie si le niveau de difficulté est valide pour un quiz qui contient
     * plusieurs types de questions. Sa validité est confirmé à partir du moment où il possède un rang défini
     * afin d'assurer une souplesse sur les quiz à type multiple.
     * </p>
     *
     * @param quizId l'identifiant du quiz
     * @param difficultyId l'identifiant du niveau de difficulté
     * @throws NotFoundException si le niveau de difficulté n'est pas valide pour le quiz
     */
    private void validateDifficultyForMultipleQuestionTypes(long quizId, long difficultyId) {
        log.debug("Validation du niveau de difficulté {} pour un quiz avec plusieurs types de questions.", difficultyId);

        if (!difficultyLevelRepository.existsValidDifficultyForQuiz(quizId, difficultyId)) {
            log.warn("Le niveau de difficulté {} n'est pas valide pour le quiz avec l'ID {}.", difficultyId, quizId);
            throw new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND);
        }
    }


}
