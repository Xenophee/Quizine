package com.dassonville.api.dto.request;

import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.GameType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Représente les données nécessaires pour créer ou mettre à jour une question de quiz.")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassicQuestionInsertDTO.class, name = AppConstants.CLASSIC_QUESTION_TYPE + AppConstants.INSERT_SUFFIX),
        @JsonSubTypes.Type(value = ClassicQuestionUpdateDTO.class, name = AppConstants.CLASSIC_QUESTION_TYPE + AppConstants.UPDATE_SUFFIX),
        @JsonSubTypes.Type(value = TrueFalseQuestionUpsertDTO.class, name = AppConstants.TRUE_FALSE_QUESTION_TYPE)
})
public interface QuestionUpsertDTO {

    @Schema(description = "Type de la question.", example = AppConstants.TRUE_FALSE_QUESTION_TYPE)
    GameType type();

    @Schema(description = "Contenu de la question.")
    String text();

    @Schema(description = "Explication de la réponse à la question.")
    String answerExplanation();
}
