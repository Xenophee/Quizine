package com.dassonville.api.exception;

public class AlreadyExistException extends RuntimeException {

    // Constructeur avec message personnalisé
    public AlreadyExistException(String message) {
        super(message);
    }

    // Constructeur avec message par défaut
    public AlreadyExistException() {
        super("L'élément existe déjà !");
    }
}

