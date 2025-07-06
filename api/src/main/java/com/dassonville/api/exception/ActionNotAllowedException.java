package com.dassonville.api.exception;

import lombok.Getter;

@Getter
public class ActionNotAllowedException extends RuntimeException {
    private final ErrorCode errorCode;

    public ActionNotAllowedException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public ActionNotAllowedException() {
        this(ErrorCode.ACTION_NOT_ALLOWED);
    }
}
