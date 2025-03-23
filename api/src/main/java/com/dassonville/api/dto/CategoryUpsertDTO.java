package com.dassonville.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record CategoryUpsertDTO(
        @NotEmpty(message = "Veuillez saisir un nom de catégorie.")
        @Size(max = 50, message = "Le nom de la catégorie ne doit pas dépasser 50 caractères.")
        String name,

        @Size(max = 250, message = "La description ne doit pas dépasser 250 caractères.")
        String description,

        @NotNull(message = "Veuillez sélectionner un thème associé.")
        @Positive(message = "Veuillez sélectionner un thème associé valide.")
        long themeId
) {
}
