package com.dassonville.api.dto;

import java.time.LocalDate;

public record CategoryDTO(
        long id,
        String name,
        String description,
        LocalDate createdAt,
        LocalDate updatedAt,
        LocalDate disabledAt,
        long themeId
) {
}
