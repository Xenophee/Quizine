package com.dassonville.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ThemeAdminDTO(
        long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        List<CategoryInfoThemeAdminDTO> categories
) {
}
