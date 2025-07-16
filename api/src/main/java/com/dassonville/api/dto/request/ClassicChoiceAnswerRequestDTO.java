package com.dassonville.api.dto.request;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.constant.GameType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Représente une réponse à une question de type Classique sous forme de choix multiples.")
@JsonTypeName(AppConstants.CLASSIC_CHOICE_ANSWER_TYPE)
public record ClassicChoiceAnswerRequestDTO(

        @Schema(
                description = """ 
                        Type de jeu utilisé pour déterminer comment la réponse est interprétée.
                        ⚠️ Obligatoire pour que l'API sache comment désérialiser correctement la réponse.
                        """,
                allowableValues = AppConstants.CLASSIC_CHOICE_ANSWER_TYPE)
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

        @Schema(description = "Liste des identifiants des réponses sélectionnées par l'utilisateur.", example = "[1, 2, 3]")
        @NotNull(message = FieldConstraint.CheckAnswer.ANSWERS_NOT_NULL)
        List<Long> selectedAnswerIds,

        Integer timeSpentInSeconds

) implements QuestionAnswerRequestDTO {

        @JsonProperty("type")
        public String getJsonType() {
                return type.getAnswerType();
        }

}

