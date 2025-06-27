package com.dassonville.api.dto.request;

import com.dassonville.api.constant.FieldConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour une catégorie.")
public record CategoryUpsertDTO(

        @Schema(description = "Nom de la catégorie", example = "Astronomie")
        @NotBlank(message = FieldConstraint.Category.NAME_NOT_BLANK)
        @Size(
                min = FieldConstraint.Category.NAME_MIN,
                max = FieldConstraint.Category.NAME_MAX,
                message = FieldConstraint.Category.NAME_SIZE
        )
        String name,

        @Schema(description = "Description de la catégorie",
                example = "La catégorie Astronomie couvre les planètes, les étoiles, les galaxies et les phénomènes célestes."
        )
        @NotBlank(message = FieldConstraint.Category.DESCRIPTION_NOT_BLANK)
        @Size(
                min = FieldConstraint.Category.DESCRIPTION_MIN,
                max = FieldConstraint.Category.DESCRIPTION_MAX,
                message = FieldConstraint.Category.DESCRIPTION_SIZE
        )
        String description
) {
}
