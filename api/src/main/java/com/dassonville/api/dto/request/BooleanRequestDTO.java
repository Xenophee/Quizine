package com.dassonville.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record BooleanRequestDTO(
        @NotNull(message = "Veuillez indiquer une valeur.")
        Boolean value
) {
}
