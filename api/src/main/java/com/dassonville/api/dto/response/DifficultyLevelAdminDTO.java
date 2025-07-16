package com.dassonville.api.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DifficultyLevelAdminDTO(
        long id,
        String name,
        boolean isReference,
        String label,
        String description,
        byte rank,
        LocalDate startsAt,
        LocalDate endsAt,
        boolean isRecurring,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt
) {
}
