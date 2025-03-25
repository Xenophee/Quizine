package com.dassonville.api.dto;

public record DifficultyLevelPublicDTO(
        long id,
        String name,
        byte maxResponses,
        short timerSeconds,
        int pointsPerQuestion,
        boolean isNew
) {
}
