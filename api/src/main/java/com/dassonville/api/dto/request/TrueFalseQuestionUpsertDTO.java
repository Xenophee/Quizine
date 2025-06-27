package com.dassonville.api.dto.request;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.constant.GameType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour une question de type Vrai/Faux.")
@JsonTypeName(AppConstants.TRUE_FALSE_QUESTION_TYPE)
public record TrueFalseQuestionUpsertDTO(

        @Schema(
                description = """ 
                        Type de la question.
                        ⚠️ Obligatoire pour que l'API sache comment désérialiser correctement la réponse.
                        """,
                allowableValues = AppConstants.TRUE_FALSE_QUESTION_TYPE)
        @NotNull(message = FieldConstraint.Question.TYPE_NOT_NULL)
        GameType type,

        @Schema(description = "Contenu de la question.", example = "La Terre est ronde.")
        @NotBlank(message = FieldConstraint.Question.TEXT_NOT_BLANK)
        @Size(
                min = FieldConstraint.Question.TEXT_MIN,
                message = FieldConstraint.Question.TEXT_SIZE
        )
        String text,

        @Schema(description = "Explication de la réponse à la question.", example = "La Terre est ronde car elle est sphérique.")
        @NotBlank(message = FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK)
        @Size(
                min = FieldConstraint.Question.ANSWER_EXPLANATION_MIN,
                message = FieldConstraint.Question.ANSWER_EXPLANATION_SIZE
        )
        String answerExplanation,

        @Schema(description = "Indique si l'affirmation de la question est vraie ou fausse.", example = "true")
        @NotNull(message = FieldConstraint.Question.ANSWER_IF_TRUE_FALSE_NOT_NULL)
        Boolean answer

) implements QuestionUpsertDTO {
}
