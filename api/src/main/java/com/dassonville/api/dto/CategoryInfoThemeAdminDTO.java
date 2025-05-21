package com.dassonville.api.dto;

import java.time.LocalDateTime;

public record CategoryInfoThemeAdminDTO(
        long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt
) {
}
