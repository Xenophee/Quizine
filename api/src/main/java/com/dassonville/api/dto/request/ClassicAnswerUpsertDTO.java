package com.dassonville.api.dto.request;

import com.dassonville.api.constant.FieldConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour une réponse de type classique.")
public record ClassicAnswerUpsertDTO(

        @Schema(description = "Contenu de la réponse.", example = "Paris")
        @NotBlank(message = FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK)
        @Size(
                min = FieldConstraint.ClassicAnswer.TEXT_MIN,
                message = FieldConstraint.ClassicAnswer.TEXT_SIZE
        )
        String text,

        @Schema(description = "Indique si la réponse est correcte ou non.", example = "true")
        @NotNull(message = FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL)
        Boolean isCorrect
) {
}
