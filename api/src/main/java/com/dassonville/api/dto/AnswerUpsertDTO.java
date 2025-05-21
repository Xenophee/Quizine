package com.dassonville.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnswerUpsertDTO(
        @NotBlank(message = "Veuillez saisir un texte pour la réponse.")
        @Size(max = 150, message = "Le texte de la réponse ne doit pas dépasser 150 caractères.")
        String text,

        boolean isCorrect
) {
}
