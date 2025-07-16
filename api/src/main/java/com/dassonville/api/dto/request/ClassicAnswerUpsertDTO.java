package com.dassonville.api.dto.request;

import com.dassonville.api.constant.FieldConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour une réponse de type classique.")
public record ClassicAnswerUpsertDTO(

        @Schema(description = "Contenu de la réponse.", example = "Paris")
        @NotBlank(message = FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK)
        String text,

        @Schema(description = "Indique si la réponse est correcte ou non.", example = "true")
        @NotNull(message = FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL)
        Boolean isCorrect
) {
}
