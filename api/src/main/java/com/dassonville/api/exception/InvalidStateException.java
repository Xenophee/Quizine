package com.dassonville.api.exception;


import lombok.Getter;

@Getter
public class InvalidStateException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidStateException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public InvalidStateException() {
        this(ErrorCode.INVALID_STATE);
    }
}
