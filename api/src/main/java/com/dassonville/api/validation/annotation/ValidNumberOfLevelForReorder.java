package com.dassonville.api.validation.annotation;


import com.dassonville.api.validation.validator.ValidNumberOfLevelForReorderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidNumberOfLevelForReorderValidator.class)
public @interface ValidNumberOfLevelForReorder {
    String message() default "Le nombre de niveaux de difficulté renseigné est invalide.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
