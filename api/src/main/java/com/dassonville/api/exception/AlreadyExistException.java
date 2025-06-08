package com.dassonville.api.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyExistException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public AlreadyExistException() {
        this(ErrorCode.UNEXPECTED_ERROR);
    }
}

