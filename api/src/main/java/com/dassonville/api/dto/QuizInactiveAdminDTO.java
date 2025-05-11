package com.dassonville.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record QuizInactiveAdminDTO(
        long id,
        String title,
        boolean isVipOnly,
        @Schema(type = "integer", format = "int32")
        byte numberOfQuestions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        IdNameDTO theme,
        IdNameDTO category
) {
}
