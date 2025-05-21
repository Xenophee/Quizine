package com.dassonville.api.exception;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException() { super("Action non autorisée !"); }
}
