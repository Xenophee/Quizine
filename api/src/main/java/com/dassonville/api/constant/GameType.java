package com.dassonville.api.constant;

import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
public enum GameType {

    CLASSIC_CHOICE(AppConstants.CLASSIC_TYPE, AppConstants.CLASSIC_QUESTION_TYPE, AppConstants.CLASSIC_CHOICE_ANSWER_TYPE),
    CLASSIC_TEXT(AppConstants.CLASSIC_TYPE, AppConstants.CLASSIC_QUESTION_TYPE, AppConstants.CLASSIC_TEXT_ANSWER_TYPE),
    TRUE_FALSE(AppConstants.TRUE_FALSE_TYPE, AppConstants.TRUE_FALSE_QUESTION_TYPE, AppConstants.TRUE_FALSE_ANSWER_TYPE),
    MIXTE(AppConstants.MIXTE_TYPE, null, null);


    private final String quizType;
    private final String questionType;
    private final String answerType;


    GameType(String quizType, String questionType, String answerType) {
        this.quizType = quizType;
        this.questionType = questionType;
        this.answerType = answerType;
    }

    @JsonValue
    public String getQuizType() {
        return this.quizType;
    }


    @JsonCreator
    public static GameType fromJson(String value) {
        log.debug("Désérialisation à partir de la valeur : {}", value);

        String normalizedValue = value
                .replace(AppConstants.INSERT_SUFFIX, "")
                .replace(AppConstants.UPDATE_SUFFIX, "");

        return Arrays.stream(values())
                .filter(type -> type.quizType.equalsIgnoreCase(normalizedValue)) // Priorité 1
                .findFirst()
                .or(() -> Arrays.stream(values())
                        .filter(type -> type.questionType != null && type.questionType.equalsIgnoreCase(normalizedValue)) // Priorité 2
                        .findFirst())
                .or(() -> Arrays.stream(values())
                        .filter(type -> type.answerType != null && type.answerType.equalsIgnoreCase(normalizedValue)) // Priorité 3
                        .findFirst())
                .map(type -> {
                    log.debug("GameType retenu : {}", type);
                    return type;
                })
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.CHECK_ANSWER_TYPE_NOT_SUPPORTED));
    }

}
