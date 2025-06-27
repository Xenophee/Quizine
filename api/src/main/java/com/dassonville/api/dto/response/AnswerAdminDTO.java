package com.dassonville.api.dto.response;

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
