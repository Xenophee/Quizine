package com.dassonville.api.exception;


import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public NotFoundException() {
        this(ErrorCode.NOT_FOUND);
    }
}

