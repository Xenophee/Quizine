package com.dassonville.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CategoryUpsertDTO(
        @NotBlank(message = "Veuillez saisir un nom de catégorie.")
        @Size(max = 50, message = "Le nom de la catégorie ne doit pas dépasser 50 caractères.")
        String name,

        @Size(max = 250, message = "La description ne doit pas dépasser 250 caractères.")
        String description
) {
}
