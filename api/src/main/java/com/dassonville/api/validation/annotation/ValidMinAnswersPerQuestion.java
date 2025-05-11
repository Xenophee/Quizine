package com.dassonville.api.validation.annotation;

import com.dassonville.api.validation.validator.ValidMinAnswersPerQuestionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidMinAnswersPerQuestionValidator.class)
public @interface ValidMinAnswersPerQuestion {
    String message() default "Le nombre de réponses est invalide.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
