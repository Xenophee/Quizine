package com.dassonville.api.validation.validator;


import com.dassonville.api.dto.ReorderRequestDTO;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.validation.annotation.ValidNumberOfLevelForReorder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidNumberOfLevelForReorderValidator implements ConstraintValidator<ValidNumberOfLevelForReorder, ReorderRequestDTO> {

    private final DifficultyLevelRepository difficultyLevelRepository;

    @Override
    public boolean isValid(ReorderRequestDTO dto, ConstraintValidatorContext context) {

        int numberOfLevels = difficultyLevelRepository.countBy();

        if (dto.orderedIds().size() != numberOfLevels) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La liste doit contenir exactement " + numberOfLevels + " niveaux pour le réordonnancement.")
                    .addPropertyNode("orderedIds")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
