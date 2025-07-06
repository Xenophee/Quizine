package com.dassonville.api.exception;

import lombok.Getter;

@Getter
public class MisconfiguredException extends RuntimeException {
    private final ErrorCode errorCode;

    public MisconfiguredException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessage(), messageArgs));
        this.errorCode = errorCode;
    }

    public MisconfiguredException() {
        this(ErrorCode.MISCONFIGURED);
    }
}
