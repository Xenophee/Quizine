package com.dassonville.api.exception;

import com.dassonville.api.util.ValidationErrorUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, Integer> CONSTRAINT_PRIORITY = Map.of(
            "NotBlank", 1,
            "NotNull", 1,
            "NotEmpty", 1,
            "Size", 2,
            "Pattern", 3,
            "Email", 4
    );



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExistException(AlreadyExistException ex) {
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Error handleAlreadyExistException(ActionNotAllowedException ex) {
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(MisconfiguredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleQuizMisconfiguredException(MisconfiguredException ex) {
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidArgumentException(InvalidArgumentException ex) {
        log.warn("Une erreur de validation a été détectée : {}", ex.getMessage());
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }


    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleInvalidStateException(InvalidStateException ex) {
        log.error("Une erreur inattendue s'est produite : {}", ex.getMessage());
        return new Error(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Une erreur de désérialisation s'est produite : {}", ex.getMessage());
        return new Error(
                ErrorCode.DESERIALIZATION_ERROR.getCode(),
                ErrorCode.DESERIALIZATION_ERROR.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleGeneralException(Exception ex) {
        log.error("Une erreur inattendue s'est produite : {}", ex.getMessage());
        return new Error(
                ErrorCode.UNEXPECTED_ERROR.getCode(),
                ErrorCode.UNEXPECTED_ERROR.getMessage()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Erreurs de validation détectées : {}", ex.getMessage());

        Map<String, FieldError> fieldErrorsByPriority = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            String code = error.getCode();

            int priority = CONSTRAINT_PRIORITY.getOrDefault(code, Integer.MAX_VALUE);

            if (!fieldErrorsByPriority.containsKey(field) ||
                    priority < CONSTRAINT_PRIORITY.getOrDefault(fieldErrorsByPriority.get(field).getCode(), Integer.MAX_VALUE)) {
                fieldErrorsByPriority.put(field, error);
            }
        }

        Map<String, Object> errors = new HashMap<>();

        for (Map.Entry<String, FieldError> entry : fieldErrorsByPriority.entrySet()) {
            String[] parts = entry.getKey().split("\\.");
            Map<String, Object> current = errors;

            for (int i = 0; i < parts.length - 1; i++) {
                current = ValidationErrorUtils.navigateOrCreate(current, parts[i]);
            }

            current.put(parts[parts.length - 1], entry.getValue().getDefaultMessage());
        }


        Map<String, Object> response = new HashMap<>();
        response.put("code", ErrorCode.VALIDATION_ERROR.getCode());
        response.put("message", ErrorCode.VALIDATION_ERROR.getMessage());
        response.put("errors", errors);

        log.warn("Les vérifications de validation ont détecté des erreurs : {}", errors);
        return response;
    }
}