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
public enum RequestActionType {
    CLASSIC_INSERT(AppConstants.CLASSIC_TYPE + AppConstants.INSERT_SUFFIX, AppConstants.CLASSIC_TYPE),
    CLASSIC_UPDATE(AppConstants.CLASSIC_TYPE + AppConstants.UPDATE_SUFFIX, AppConstants.CLASSIC_TYPE),
    TRUE_FALSE(AppConstants.TRUE_FALSE_TYPE, AppConstants.TRUE_FALSE_TYPE);

    private final String value;
    private final String type;

    RequestActionType(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RequestActionType fromValue(String value) {

        log.debug("Désérialisation à partir de la valeur : {}", value);

        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .map(type -> {
                    log.debug("Type retenu : {}", type);
                    return type;
                })
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.TYPE_NOT_SUPPORTED));
    }

}

