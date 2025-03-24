package com.dassonville.api.dto;

import java.time.LocalDate;
import java.util.List;

public record ThemeAdminDTO(
        long id,
        String name,
        String description,
        LocalDate createdAt,
        LocalDate updatedAt,
        LocalDate disabledAt,
        List<CategoryAdminDTO> categories
) {
}
