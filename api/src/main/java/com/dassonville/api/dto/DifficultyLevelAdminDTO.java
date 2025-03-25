package com.dassonville.api.dto;

import java.time.LocalDate;

public record DifficultyLevelAdminDTO(
        long id,
        String name,
        byte maxResponses,
        short timerSeconds,
        int pointsPerQuestion,
        LocalDate createdAt,
        LocalDate updatedAt,
        LocalDate disabledAt
) {
}
