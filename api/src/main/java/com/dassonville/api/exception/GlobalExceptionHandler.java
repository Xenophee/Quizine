package com.dassonville.api.exception;

import com.dassonville.api.util.ValidationErrorUtils;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExistException(AlreadyExistException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExistException(ActionNotAllowedException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Une erreur de validation a été détectée : {}", ex.getMessage());
        return new Error(ex.getMessage());
    }


    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleIllegalStateException(IllegalStateException ex) {
        logger.error("Une erreur inattendue s'est produite : {}", ex.getMessage());
        return new Error("Une erreur inattendue s'est produite.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleGeneralException(Exception ex) {
        logger.error("Une erreur inattendue s'est produite : {}", ex.getMessage());
        return new Error("Une erreur inattendue s'est produite.");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String[] parts = error.getField().split("\\.");
            Map<String, Object> current = errors;

            for (int i = 0; i < parts.length - 1; i++) {
                current = ValidationErrorUtils.navigateOrCreate(current, parts[i]);
            }

            current.put(parts[parts.length - 1], error.getDefaultMessage());
        });

        logger.warn("Les vérifications de validation ont détecté des erreurs : {}", errors);
        return errors;
    }
}