package com.dassonville.api.validation.validator;

import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.dto.request.QuizUpsertDTO;
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
            context.buildConstraintViolationWithTemplate(FieldConstraint.Quiz.THEME_NOT_FOUND)
                    .addPropertyNode("themeId")
                    .addConstraintViolation();
            return false;
        }

        if (dto.categoryId() != null && !categoryRepository.existsByIdAndThemeId(dto.categoryId(), dto.themeId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FieldConstraint.Quiz.CATEGORY_NOT_BELONG_TO_THEME)
                    .addPropertyNode("categoryId")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

