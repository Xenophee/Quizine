package com.dassonville.api.dto.request;

import com.dassonville.api.constant.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Représente une réponse à une question de type Classique sous forme d'entrée libre.")
@JsonTypeName(AppConstants.CLASSIC_TEXT_ANSWER_TYPE)
public record ClassicTextAnswerRequestDTO(

        @Schema(
                description = """ 
                        Type de jeu utilisé pour déterminer comment la réponse est interprétée.
                        ⚠️ Obligatoire pour que l'API sache comment désérialiser correctement la réponse.
                        """,
                allowableValues = AppConstants.CLASSIC_TEXT_ANSWER_TYPE)
        @NotNull(message = FieldConstraint.CheckAnswer.TYPE_NOT_NULL)
        GameType type,

        @NotNull(message = FieldConstraint.CheckAnswer.QUIZ_ID_NOT_NULL)
        Long quizId,
        @NotNull(message = FieldConstraint.CheckAnswer.QUESTION_ID_NOT_NULL)
        Long questionId,
        @NotNull(message = FieldConstraint.CheckAnswer.DIFFICULTY_ID_NOT_NULL)
        Long difficultyId,
        @NotNull(message = FieldConstraint.CheckAnswer.TIMER_ENABLED_NOT_NULL)
        Boolean isTimerEnabled,
        @NotNull(message = FieldConstraint.CheckAnswer.PENALTIES_ENABLED_NOT_NULL)
        Boolean isPenaltiesEnabled,

        @Schema(description = "Liste des réponses textuelles soumises par l'utilisateur en réponse à la question.", example = "[\"Réponse 1\", \"Réponse 2\"]")
        @NotNull(message = FieldConstraint.CheckAnswer.ANSWERS_NOT_NULL)
        List<String> submittedTexts,

        Integer timeSpentInSeconds

) implements QuestionAnswerRequestDTO {

        @JsonProperty("type")
        public String getJsonType() {
                return type.getAnswerType();
        }
}

