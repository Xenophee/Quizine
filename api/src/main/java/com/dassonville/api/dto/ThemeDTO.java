package com.dassonville.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ThemeDTO(
        long id,
        String name,
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime updatedAt,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime disabledAt
) {
}
