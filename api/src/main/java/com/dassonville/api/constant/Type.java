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
public enum Type {

    CLASSIC(AppConstants.CLASSIC_TYPE),
    TRUE_FALSE(AppConstants.TRUE_FALSE_TYPE),
    MIXTE(AppConstants.MIXTE_TYPE);

    private final String value;


    Type(String value) {
        this.value = value;
    }

    @JsonValue
    public String getType() {
        return this.value;
    }

    @JsonCreator
    public static Type fromValue(String value) {
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
