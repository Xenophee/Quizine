package com.dassonville.api.dto;

import java.time.LocalDateTime;

public record DifficultyLevelAdminDTO(
        long id,
        String name,
        byte answerOptionsCount,
        short timerSeconds,
        int pointsPerQuestion,
        boolean isReference,
        byte displayOrder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt
) {
}
