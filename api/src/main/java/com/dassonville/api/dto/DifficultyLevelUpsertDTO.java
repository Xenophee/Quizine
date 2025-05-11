package com.dassonville.api.dto;

import jakarta.validation.constraints.*;


public record DifficultyLevelUpsertDTO(
        @NotEmpty(message = "Veuillez indiquer un nom de difficulté.")
        @Size(max = 50, message = "Le nom de la difficulté ne doit pas dépasser 50 caractères.")
        String name,

        @NotNull(message = "Veuillez indiquer le nombre de réponses minimum.")
        @Min(value = 0, message = "Le nombre de réponses ne doit pas être négatif.")
        byte maxResponses,

        @Min(value = 0, message = "Le timer ne doit pas être négatif.")
        short timerSeconds,

        @NotNull(message = "Veuillez indiquer le nombre de points par bonne réponse.")
        @Min(value = 1, message = "Le nombre de points doit être supérieur à 0.")
        int pointsPerQuestion
) {
}
