package com.dassonville.api.validation.validator;


import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.repository.GameRuleRepository;
import com.dassonville.api.validation.annotation.ValidMinAnswersPerQuestion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class ValidMinAnswersPerQuestionValidator implements ConstraintValidator<ValidMinAnswersPerQuestion, List<ClassicAnswerUpsertDTO>> {

    private final GameRuleRepository gameRuleRepository;

    @Override
    public boolean isValid(List<ClassicAnswerUpsertDTO> answers, ConstraintValidatorContext context) {
        byte answerOptionsCount = gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(AppConstants.CLASSIC_QUESTION_TYPE)
                .orElseThrow(() -> {
                    log.error("Aucune règle de jeu trouvée pour le type de question {} !", AppConstants.CLASSIC_QUESTION_TYPE);
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        if (answers == null || answers.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Veuillez fournir au moins " + answerOptionsCount + " réponses.")
                    .addConstraintViolation();
            return false;
        }

        if (answers.size() < answerOptionsCount) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Veuillez fournir au moins " + answerOptionsCount + " réponses.")
                    .addConstraintViolation();
            return false;
        }

        boolean hasCorrectAnswer = answers.stream().anyMatch(ClassicAnswerUpsertDTO::isCorrect);
        if (!hasCorrectAnswer) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FieldConstraint.Question.AT_LEAST_ONE_CORRECT_ANSWER_REQUIRED)
                    .addConstraintViolation();
            return false;
        }

        long distinctCount = answers.stream()
                .map(ClassicAnswerUpsertDTO::text)
                .filter(text -> text != null && !text.trim().isEmpty())
                .map(text -> text.trim().toLowerCase())
                .distinct()
                .count();

        long validCount = answers.stream()
                .map(ClassicAnswerUpsertDTO::text)
                .filter(text -> text != null && !text.trim().isEmpty())
                .count();

        if (distinctCount != validCount) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FieldConstraint.Question.ONLY_UNIQUE_ANSWERS_ALLOWED)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
