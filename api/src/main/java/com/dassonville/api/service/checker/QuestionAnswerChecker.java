package com.dassonville.api.service.checker;

import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.response.CheckAnswerResultDTO;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;


public interface QuestionAnswerChecker {
    boolean supports(GameType type);
    CheckAnswerResultDTO checkAnswer(QuestionAnswerRequestDTO request, boolean isQuizSoloQuestionType);
}

