package com.dassonville.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record QuizAdminDTO(
        long id,
        String title,
        boolean isVipOnly,
        @Schema(type = "integer", format = "int32")
        byte numberOfQuestions,
        boolean hasEnoughQuestionsForActivation,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        IdNameDTO category
) {
}
