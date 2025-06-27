package com.dassonville.api.dto.request;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.constant.GameType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "Représente les données nécessaires pour mettre à jour une question de type classique.")
@JsonTypeName(AppConstants.CLASSIC_QUESTION_TYPE + AppConstants.UPDATE_SUFFIX)
public record ClassicQuestionUpdateDTO(

        @Schema(
                description = """ 
                        Type de la question.
                        ⚠️ Obligatoire pour que l'API sache comment désérialiser correctement la réponse.
                        """,
                allowableValues = AppConstants.CLASSIC_QUESTION_TYPE + AppConstants.UPDATE_SUFFIX)
        @NotNull(message = FieldConstraint.Question.TYPE_NOT_NULL)
        GameType type,

        @Schema(description = "Contenu de la question.", example = "Quelle est la capitale de la France ?")
        @NotBlank(message = FieldConstraint.Question.TEXT_NOT_BLANK)
        @Size(
                min = FieldConstraint.Question.TEXT_MIN,
                message = FieldConstraint.Question.TEXT_SIZE
        )
        String text,

        @Schema(description = "Explication de la réponse à la question.",
                example = "La capitale de la France est Paris, car c'est le centre politique et culturel du pays.")
        @NotBlank(message = FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK)
        @Size(
                min = FieldConstraint.Question.ANSWER_EXPLANATION_MIN,
                message = FieldConstraint.Question.ANSWER_EXPLANATION_SIZE
        )
        String answerExplanation

) implements QuestionUpsertDTO {
}
