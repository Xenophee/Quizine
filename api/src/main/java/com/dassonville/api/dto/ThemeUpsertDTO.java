package com.dassonville.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ThemeUpsertDTO(

        @NotEmpty(message = "Veuillez saisir un nom de thème.")
        @Size(max = 50, message = "Le nom du thème ne doit pas dépasser 70 caractères.")
        String name,

        @Size(max = 250, message = "La description ne doit pas dépasser 250 caractères.")
        String description
) {}

