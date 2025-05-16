package com.dassonville.api.validation.validator;


import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.validation.annotation.ValidMinAnswersPerQuestion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ValidMinAnswersPerQuestionValidator implements ConstraintValidator<ValidMinAnswersPerQuestion, QuestionInsertDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ValidMinAnswersPerQuestionValidator.class);

    private final DifficultyLevelRepository difficultyLevelRepository;

    @Override
    public boolean isValid(QuestionInsertDTO dto, ConstraintValidatorContext context) {
        byte min = difficultyLevelRepository.findReferenceLevelMaxAnswers()
                .orElseThrow(() -> {
                    logger.error("Le niveau de difficulté de référence n'a pas été trouvé.");
                    return new IllegalStateException("Le niveau de difficulté de référence n'a pas été trouvé.");
                });

        if (dto.answers() == null || dto.answers().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Veuillez fournir au moins " + min + " réponses.")
                    .addPropertyNode("answers")
                    .addConstraintViolation();
            return false;
        }

        if (dto.answers().size() < min) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Veuillez fournir au moins " + min + " réponses.")
                    .addPropertyNode("answers")
                    .addConstraintViolation();
            return false;
        }

        boolean hasCorrectAnswer = dto.answers().stream().anyMatch(AnswerUpsertDTO::isCorrect);
        if (!hasCorrectAnswer) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Au moins une réponse correcte est requise.")
                    .addPropertyNode("answers")
                    .addConstraintViolation();
            return false;
        }

        long distinctCount = dto.answers().stream()
                .map(AnswerUpsertDTO::text)
                .filter(text -> text != null && !text.trim().isEmpty())
                .map(text -> text.trim().toLowerCase())
                .distinct()
                .count();

        long validCount = dto.answers().stream()
                .map(AnswerUpsertDTO::text)
                .filter(text -> text != null && !text.trim().isEmpty())
                .count();

        if (distinctCount != validCount) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Chaque réponse doit avoir un texte unique.")
                    .addPropertyNode("answers")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
