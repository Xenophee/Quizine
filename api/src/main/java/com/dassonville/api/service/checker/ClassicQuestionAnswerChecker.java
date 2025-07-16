package com.dassonville.api.service.checker;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.request.ClassicChoiceAnswerRequestDTO;
import com.dassonville.api.dto.request.ClassicTextAnswerRequestDTO;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.response.AnswerForPlayDTO;
import com.dassonville.api.dto.response.CheckClassicAnswerResultDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidArgumentException;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.repository.ClassicAnswerRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.service.ScoreComputationService;
import com.dassonville.api.service.scoring.ScoreComputationContext;
import com.dassonville.api.service.scoring.ScoreComputationResult;
import com.dassonville.api.util.TextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassicQuestionAnswerChecker implements QuestionAnswerChecker {

    private final ScoreComputationService scoreComputationService;
    private final ClassicAnswerRepository classicAnswerRepository;
    private final QuestionRepository questionRepository;



    @Override
    public boolean supports(GameType type) {
        return Objects.equals(type.getMainType(), AppConstants.CLASSIC_TYPE);
    }

    @Override
    public CheckClassicAnswerResultDTO checkAnswer(QuestionAnswerRequestDTO request, boolean isQuizSoloQuestionType) {

        CheckClassicAnswerResultDTO baseResult;

        switch (request) {
            case ClassicChoiceAnswerRequestDTO choiceRequest -> baseResult = checkByChoice(choiceRequest);
            case ClassicTextAnswerRequestDTO textRequest -> baseResult = checkByText(textRequest);
            default -> throw new InvalidStateException(ErrorCode.CHECK_ANSWER_TYPE_NOT_SUPPORTED);
        }

        ScoreComputationContext scoreComputationContext = new ScoreComputationContext(
                request.type(),
                request.quizId(),
                request.difficultyId(),
                baseResult.isCorrect(),
                request.isTimerEnabled(),
                request.isPenaltiesEnabled(),
                request.timeSpentInSeconds()
        );

        ScoreComputationResult result = scoreComputationService.computeScore(scoreComputationContext, isQuizSoloQuestionType);

        return new CheckClassicAnswerResultDTO(
                baseResult.isCorrect(),
                result.isInTime(),
                result.score(),
                result.message(),
                baseResult.answerExplanation(),
                baseResult.correctAnswers()
        );
    }


    /**
     * Vérifie les réponses à choix multiples soumises par l'utilisateur.
     *
     * @param request La requête contenant les réponses à choix multiples sous la forme d'une instance de {@link ClassicChoiceAnswerRequestDTO}.
     * @return Un objet {@link CheckClassicAnswerResultDTO} contenant le résultat de la vérification.
     * @throws InvalidArgumentException Si les réponses soumises ne correspondent pas à la question.
     * @throws InvalidStateException Si la question n'a pas de bonne réponse ou si l'explication de la réponse est manquante.
     */
    private CheckClassicAnswerResultDTO checkByChoice(ClassicChoiceAnswerRequestDTO request) {

        // Vérifie que les réponses soumises appartiennent bien à la question
        long validCount = classicAnswerRepository.countActiveValidAnswers(request.selectedAnswerIds(), request.questionId());
        if (validCount != request.selectedAnswerIds().size()) {
            log.warn("Une ou plusieurs réponses soumises ne correspondent pas à la question.");
            throw new InvalidArgumentException(ErrorCode.ANSWERS_AND_QUESTION_MISMATCH, request.selectedAnswerIds(), request.questionId());
        }

        // Récupère toutes les bonnes réponses
        List<ClassicAnswer> correctClassicAnswers = classicAnswerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(request.questionId());

        if (correctClassicAnswers.isEmpty()) {
            log.error("La question {} n’a pas de bonne réponse !", request.questionId());
            throw new InvalidStateException(ErrorCode.INTERNAL_ERROR);
        }

        // Vérifie si les réponses soumises sont correctes
        Set<Long> correctIds = correctClassicAnswers.stream()
                .map(ClassicAnswer::getId)
                .collect(Collectors.toSet());

        Set<Long> submittedIds = new HashSet<>(request.selectedAnswerIds());

        boolean isCorrect = submittedIds.equals(correctIds);
        log.info("Réponses soumises : {}, Attendus : {}, Résultat : {}", submittedIds, correctIds, isCorrect);

        // Mapping des bonnes réponses à retourner
        List<AnswerForPlayDTO> correctDTOList = correctClassicAnswers.stream()
                .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                .toList();

        // Récupère l'explication de la réponse
        String explanation = questionRepository.findAnswerExplanationById(request.questionId())
                .orElseThrow(() -> {
                    log.error("La question {} n'a pas d'explication associée.", request.questionId());
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        return CheckClassicAnswerResultDTO.withoutScore(isCorrect, explanation, correctDTOList);
    }



    /**
     * Vérifie les réponses textuelles soumises par l'utilisateur.
     *
     * @param request La requête contenant les réponses textuelles sous la forme d'une instance de {@link ClassicTextAnswerRequestDTO}.
     * @return Un objet {@link CheckClassicAnswerResultDTO } contenant le résultat de la vérification.
     * @throws InvalidStateException Si la question n'a pas de bonne réponse ou si l'explication de la réponse est manquante.
     */
    private CheckClassicAnswerResultDTO checkByText(ClassicTextAnswerRequestDTO request) {

        // Récupère les bonnes réponses
        List<ClassicAnswer> correctClassicAnswers = classicAnswerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(request.questionId());
        if (correctClassicAnswers.isEmpty()) {
            log.error("La question {} n’a pas de bonne réponse !", request.questionId());
            throw new InvalidStateException(ErrorCode.INTERNAL_ERROR);
        }

        // Normalisation des réponses correctes
        Set<String> correctNormalized = correctClassicAnswers.stream()
                .map(ClassicAnswer::getText)
                .map(TextUtils::normalizeAndRemoveAccents)
                .collect(Collectors.toSet());

        // Normalisation des réponses utilisateur
        Set<String> submittedNormalized = request.submittedTexts().stream()
                .map(TextUtils::normalizeAndRemoveAccents)
                .collect(Collectors.toSet());

        // Vérifie que toutes les bonnes réponses sont données, sans extra
        boolean isCorrect = submittedNormalized.equals(correctNormalized);
        log.info("Réponses correctes : {}, Réponses soumises : {}, Résultat : {}", correctNormalized, submittedNormalized, isCorrect);

        // Mapping des bonnes réponses à retourner
        List<AnswerForPlayDTO> correctDTOList = correctClassicAnswers.stream()
                .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                .toList();

        // Récupère l'explication de la réponse
        String explanation = questionRepository.findAnswerExplanationById(request.questionId())
                .orElseThrow(() -> {
                    log.error("La question {} n'a pas d'explication associée.", request.questionId());
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        return CheckClassicAnswerResultDTO.withoutScore(isCorrect, explanation, correctDTOList);
    }
}

