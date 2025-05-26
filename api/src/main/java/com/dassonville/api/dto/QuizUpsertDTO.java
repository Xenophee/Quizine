package com.dassonville.api.dto;


import com.dassonville.api.validation.annotation.ValidCategoryForTheme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@ValidCategoryForTheme
public record QuizUpsertDTO(
        @NotBlank(message = "Veuillez saisir un titre pour le quiz.")
        @Size(max = 100, message = "Le titre du quiz ne doit pas dépasser 100 caractères.")
        String title,
        Long categoryId,
        @NotNull(message = "Veuillez sélectionner un thème.")
        @Positive(message = "L'identifiant du thème doit être positif.")
        Long themeId
) {
}
