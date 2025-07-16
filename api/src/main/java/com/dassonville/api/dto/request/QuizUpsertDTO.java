package com.dassonville.api.dto.request;


import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.constant.Type;
import com.dassonville.api.validation.annotation.ValidCategoryForTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour un quiz.")
@ValidCategoryForTheme
public record QuizUpsertDTO(

        @Schema(description = "Type du quiz.", example = AppConstants.CLASSIC_TYPE,
                allowableValues = {AppConstants.CLASSIC_TYPE, AppConstants.TRUE_FALSE_TYPE, AppConstants.MIXTE_TYPE})
        @NotNull(message = FieldConstraint.Quiz.TYPE_NOT_NULL)
        Type type,

        @Schema(description = "Titre du quiz", example = "La Constitution de la Ve République")
        @NotBlank(message = FieldConstraint.Quiz.TITLE_NOT_BLANK)
        @Size(
                min = FieldConstraint.Quiz.TITLE_MIN,
                max = FieldConstraint.Quiz.TITLE_MAX,
                message = FieldConstraint.Quiz.TITLE_SIZE
        )
        String title,

        @Schema(description = "Description du quiz",
                example = "Ce quiz teste vos connaissances sur la Constitution de la Ve République française, y compris ses principes fondamentaux et son histoire."
        )
        @NotBlank(message = FieldConstraint.Quiz.DESCRIPTION_NOT_BLANK)
        @Size(
                min = FieldConstraint.Quiz.DESCRIPTION_MIN,
                max = FieldConstraint.Quiz.DESCRIPTION_MAX,
                message = FieldConstraint.Quiz.DESCRIPTION_SIZE
        )
        String description,

        @Schema(description = "ID du niveau de maîtrise associé au quiz", example = "1")
        @NotNull(message = FieldConstraint.Quiz.MASTERY_LEVEL_NOT_NULL)
        Long masteryLevelId,

        @Schema(description = "ID du thème associé au quiz", example = "3")
        @NotNull(message = FieldConstraint.Quiz.THEME_NOT_NULL)
        Long themeId,

        @Schema(description = "ID de la catégorie associée au quiz", example = "4")
        Long categoryId
) {
}
