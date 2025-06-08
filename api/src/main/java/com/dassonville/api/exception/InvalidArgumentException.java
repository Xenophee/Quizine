package com.dassonville.api.exception;


import lombok.Getter;

@Getter
public class InvalidArgumentException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidArgumentException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public InvalidArgumentException() {
        this(ErrorCode.UNEXPECTED_ERROR);
    }
}
