package com.dassonville.api.dto.request;

import com.dassonville.api.constant.FieldConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour un thème.")
public record ThemeUpsertDTO(

        @Schema(description = "Nom du thème", example = "Chimie")
        @NotBlank(message = FieldConstraint.Theme.NAME_NOT_BLANK)
        @Size(
                min = FieldConstraint.Theme.NAME_MIN,
                max = FieldConstraint.Theme.NAME_MAX,
                message = FieldConstraint.Theme.NAME_SIZE
        )
        String name,

        @Schema(description = "Description du thème",
                example = "Le thème de la chimie couvre les éléments chimiques, les réactions, et les principes fondamentaux de la science chimique."
        )
        @NotBlank(message = FieldConstraint.Theme.DESCRIPTION_NOT_BLANK)
        @Size(
                min = FieldConstraint.Theme.DESCRIPTION_MIN,
                max = FieldConstraint.Theme.DESCRIPTION_MAX,
                message = FieldConstraint.Theme.DESCRIPTION_SIZE
        )
        String description
) {
}

