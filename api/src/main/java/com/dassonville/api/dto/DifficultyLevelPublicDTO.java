package com.dassonville.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DifficultyLevelPublicDTO(
        long id,
        String name,
        @Schema(type = "integer", format = "int32")
        byte answerOptionsCount,
        short timerSeconds,
        int pointsPerQuestion,
        boolean isNew
) {
}
