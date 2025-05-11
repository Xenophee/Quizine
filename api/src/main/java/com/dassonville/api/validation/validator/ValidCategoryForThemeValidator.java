package com.dassonville.api.validation.validator;

import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.validation.annotation.ValidCategoryForTheme;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidCategoryForThemeValidator implements ConstraintValidator<ValidCategoryForTheme, QuizUpsertDTO> {

    private final ThemeRepository themeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(QuizUpsertDTO dto, ConstraintValidatorContext context) {

        if (!themeRepository.existsById(dto.themeId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Le thème spécifié est introuvable.")
                    .addPropertyNode("themeId")
                    .addConstraintViolation();
            return false;
        }

        if (dto.categoryId() != null && !categoryRepository.existsByIdAndThemeId(dto.categoryId(), dto.themeId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La catégorie spécifiée n'appartient pas au thème.")
                    .addPropertyNode("categoryId")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

