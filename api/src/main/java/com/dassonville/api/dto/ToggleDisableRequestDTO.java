package com.dassonville.api.dto;

import jakarta.validation.constraints.NotNull;

public record ToggleDisableRequestDTO(
        @NotNull
        boolean isDisabled
) {
}
