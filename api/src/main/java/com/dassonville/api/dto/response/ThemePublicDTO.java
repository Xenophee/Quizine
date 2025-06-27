package com.dassonville.api.dto.response;

public record ThemePublicDTO(
        long id,
        String name,
        String description,
        boolean isNew
) {
}
