package com.dassonville.api.dto;

import jakarta.validation.constraints.*;


public record DifficultyLevelUpsertDTO(
        @NotBlank(message = "Veuillez indiquer un nom de difficulté.")
        @Size(max = 50, message = "Le nom de la difficulté ne doit pas dépasser 50 caractères.")
        String name,

        @NotNull(message = "Veuillez indiquer le nombre de réponses minimum.")
        @Min(value = 0, message = "Le nombre de réponses ne doit pas être négatif.")
        Byte maxAnswers,

        @NotNull(message = "Veuillez indiquer le nombre nombre de secondes pour le timer.")
        @Min(value = 0, message = "Le timer ne doit pas être négatif.")
        Short timerSeconds,

        @NotNull(message = "Veuillez indiquer le nombre de points par bonne réponse.")
        @Positive(message = "Le nombre de points attribué doit être supérieur à 0.")
        Integer pointsPerQuestion
) {
}
