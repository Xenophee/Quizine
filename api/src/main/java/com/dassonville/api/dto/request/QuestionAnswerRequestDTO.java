package com.dassonville.api.dto.request;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.GameType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Représente une requête pour vérifier la réponse à une question de quiz.")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassicChoiceAnswerRequestDTO.class, name = AppConstants.CLASSIC_CHOICE_ANSWER_TYPE),
        @JsonSubTypes.Type(value = ClassicTextAnswerRequestDTO.class, name = AppConstants.CLASSIC_TEXT_ANSWER_TYPE),
        @JsonSubTypes.Type(value = TrueFalseAnswerRequestDTO.class, name = AppConstants.TRUE_FALSE_QUESTION_TYPE)
})
public interface QuestionAnswerRequestDTO {

    @Schema(description = "Type de la réponse à la question.", example = AppConstants.CLASSIC_CHOICE_ANSWER_TYPE)
    GameType type();

    @Schema(description = "Identifiant du quiz auquel la question appartient.", example = "1")
    Long quizId();

    @Schema(description = "Identifiant de la question à laquelle la réponse est donnée.", example = "1")
    Long questionId();

    @Schema(description = "Identifiant de la difficulté de la question.", example = "1")
    Long difficultyId();

    @Schema(name = "isTimerEnabled", description = "Indique si le minuteur est activé pour la question.", example = "true")
    Boolean isTimerEnabled();

    @Schema(name = "isPenaltiesEnabled", description = "Indique si les pénalités sont activées pour la question.", example = "false")
    Boolean isPenaltiesEnabled();

    @Schema(description = "Temps passé à répondre à la question en secondes.", example = "30")
    Integer timeSpentInSeconds();
}
