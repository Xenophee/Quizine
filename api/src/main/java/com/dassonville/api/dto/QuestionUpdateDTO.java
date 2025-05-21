package com.dassonville.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record QuestionUpdateDTO(
        @NotBlank(message = "Veuillez saisir le contenu de la question.")
        @Size(max = 300, message = "Le contenu de la question ne doit pas dépasser 300 caractères.")
        String text
) {
}
