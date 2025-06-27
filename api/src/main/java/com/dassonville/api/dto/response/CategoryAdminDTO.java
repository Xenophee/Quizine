package com.dassonville.api.dto.response;


import java.time.LocalDateTime;

public record CategoryAdminDTO(
        long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        long themeId
) {
}
