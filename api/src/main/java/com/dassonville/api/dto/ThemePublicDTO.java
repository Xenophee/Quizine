package com.dassonville.api.dto;

public record ThemePublicDTO(
        long id,
        String name,
        String description,
        boolean isNew
) {
}
