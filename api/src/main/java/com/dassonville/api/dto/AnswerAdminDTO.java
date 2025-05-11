package com.dassonville.api.dto;

import java.time.LocalDateTime;

public record AnswerAdminDTO(
        long id,
        String text,
        boolean isCorrect,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt
) {
}
