package com.dassonville.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CheckTrueFalseAnswerResultDTO(

        boolean isCorrect,
        boolean isInTime,
        int score,
        String resultMessage,
        @Schema(description = "Indique si l'assertion de la question est vraie ou fausse.", example = "true")
        boolean isAssertionTrue,
        String answerExplanation

) implements CheckAnswerResultDTO {
}
