package com.dassonville.api.exception;

public class MismatchedIdException extends RuntimeException {
    public MismatchedIdException(String message) {
        super(message);
    }
}
