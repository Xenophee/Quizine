package com.dassonville.api.service.checker;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.request.TrueFalseAnswerRequestDTO;
import com.dassonville.api.dto.response.CheckTrueFalseAnswerResultDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.projection.TrueFalseQuestionExplanationProjection;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.service.ScoreComputationService;
import com.dassonville.api.service.scoring.ScoreComputationContext;
import com.dassonville.api.service.scoring.ScoreComputationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrueFalseAnswerChecker implements QuestionAnswerChecker {

    private final ScoreComputationService scoreComputationService;
    private final QuestionRepository questionRepository;


    @Override
    public boolean supports(GameType type) {
        return Objects.equals(type.getQuestionType(), AppConstants.TRUE_FALSE_QUESTION_TYPE);
    }

    @Override
    public CheckTrueFalseAnswerResultDTO checkAnswer(QuestionAnswerRequestDTO trueFalseRequest, boolean isQuizSoloQuestionType) {

        TrueFalseAnswerRequestDTO request = (TrueFalseAnswerRequestDTO) trueFalseRequest;

        // Récupère l'explication de la réponse
        TrueFalseQuestionExplanationProjection answer = questionRepository.findTrueFalseQuestionAnswerAndExplanationById(request.questionId())
                .orElseThrow(() -> {
                    log.error("La question {} n'a pas d'explication associée.", request.questionId());
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        // Vérifie si la réponse est correcte
        boolean isCorrect = Boolean.TRUE.equals(answer.getAnswerIfTrueFalse()) == request.answer();
        log.info("Vérification de la réponse pour la question {} : Réponse donnée = {}, Réponse correcte = {}, Résultat = {}",
                request.questionId(), request.answer(), answer.getAnswerIfTrueFalse(), isCorrect);

        ScoreComputationContext scoreComputationContext = new ScoreComputationContext(
                request.type(),
                request.quizId(),
                request.difficultyId(),
                isCorrect,
                request.isTimerEnabled(),
                request.isPenaltiesEnabled(),
                request.timeSpentInSeconds()
        );

        ScoreComputationResult result = scoreComputationService.computeScore(scoreComputationContext, isQuizSoloQuestionType);

        return new CheckTrueFalseAnswerResultDTO(
                isCorrect,
                result.isInTime(),
                result.score(),
                result.message(),
                answer.getAnswerIfTrueFalse(),
                answer.getAnswerExplanation());
    }
}

