package com.dassonville.api.dto;

import java.time.LocalDateTime;

public record DifficultyLevelAdminDTO(
        long id,
        String name,
        byte maxResponses,
        short timerSeconds,
        int pointsPerQuestion,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt
) {
}
