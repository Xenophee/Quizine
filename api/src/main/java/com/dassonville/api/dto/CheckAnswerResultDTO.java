package com.dassonville.api.dto;

import java.util.List;

public record CheckAnswerResultDTO(
        boolean isCorrect,
        List<AnswerForPlayDTO> correctAnswers
) {
}
