package com.dassonville.api.dto;


import com.dassonville.api.validation.annotation.ValidCategoryForTheme;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ValidCategoryForTheme
public record QuizUpsertDTO(
        @NotEmpty(message = "Veuillez saisir un titre pour le quiz.")
        @Size(max = 100, message = "Le titre du quiz ne doit pas dépasser 100 caractères.")
        String title,
        boolean isVipOnly,
        Long categoryId,
        @NotNull(message = "Veuillez sélectionner un thème.")
        Long themeId
) {
}
