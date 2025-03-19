package com.dassonville.api.exception;

public class NotFoundException extends RuntimeException {

    // Constructeur avec message personnalisé
    public NotFoundException(String message) {
        super(message);
    }

    // Constructeur avec message par défaut
    public NotFoundException() {
        super("L'élément n'existe pas !");
    }
}
