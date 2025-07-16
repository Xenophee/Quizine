package com.dassonville.api.dto.request;

import com.dassonville.api.constant.*;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Représente une réponse à une question de type Vrai/Faux.")
@JsonTypeName(AppConstants.TRUE_FALSE_TYPE)
public record TrueFalseAnswerRequestDTO(

        @Schema(
                description = """ 
                        Type de jeu utilisé pour déterminer comment la réponse est interprétée.
                        ⚠️ Obligatoire pour que l'API sache comment désérialiser correctement la réponse.
                        """,
                allowableValues = AppConstants.TRUE_FALSE_TYPE)
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

        @Schema(description = "Indique si l'affirmation de la question est vraie ou fausse selon l'utilisateur.", example = "true")
        @NotNull(message = FieldConstraint.CheckAnswer.ANSWERS_NOT_NULL)
        Boolean answer,

        Integer timeSpentInSeconds

) implements QuestionAnswerRequestDTO {
}

