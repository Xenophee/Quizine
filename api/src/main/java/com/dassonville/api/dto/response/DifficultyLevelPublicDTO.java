package com.dassonville.api.dto.response;

public record DifficultyLevelPublicDTO(
        long id,
        String name,
        String label,
        String description,
        boolean isNew
) {
}
