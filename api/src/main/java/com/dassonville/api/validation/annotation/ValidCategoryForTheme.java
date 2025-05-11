package com.dassonville.api.validation.annotation;

import com.dassonville.api.validation.validator.ValidCategoryForThemeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCategoryForThemeValidator.class)
public @interface ValidCategoryForTheme {
    String message() default "La catégorie ne correspond pas au thème sélectionné.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

