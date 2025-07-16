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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class ValidMinAnswersPerQuestionValidator implements ConstraintValidator<ValidMinAnswersPerQuestion, List<ClassicAnswerUpsertDTO>> {

    private final GameRuleRepository gameRuleRepository;

    @Override
    public boolean isValid(List<ClassicAnswerUpsertDTO> answers, ConstraintValidatorContext context) {
        byte answerOptionsCount = gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(AppConstants.CLASSIC_TYPE)
                .orElseThrow(() -> {
                    log.error("Aucune règle de jeu trouvée pour le type de question {} !", AppConstants.CLASSIC_TYPE);
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        context.disableDefaultConstraintViolation();

        boolean valid = true;

        List<String> globalMessages = new ArrayList<>();

        // ─── Vérif 1 : null ou trop peu de réponses ──────────────────────────────
        if (answers == null || answers.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Veuillez fournir au moins " + answerOptionsCount + " réponses.")
                    .addPropertyNode("global")
                    .addConstraintViolation();
            return false;
        }

        if (answers.size() < answerOptionsCount) {
            globalMessages.add("Veuillez fournir au moins " + answerOptionsCount + " réponses.");
            valid = false;
        }

        boolean hasCorrectAnswer = false;
        Set<String> uniqueTexts = new HashSet<>();

        // ─── Vérif 2 : boucle sur chaque réponse ──────────────────────────────────
        for (int i = 0; i < answers.size(); i++) {
            ClassicAnswerUpsertDTO answer = answers.get(i);
            if (answer == null) {
                continue;
            }

            // ─ Check text non vide ─
            String text = answer.text();
            if (text == null || text.trim().isEmpty()) {
                context.buildConstraintViolationWithTemplate(FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK)
                        .addPropertyNode("answers[" + i + "].text")
                        .addConstraintViolation();
                valid = false;
            } else {
                // pour unicité
                uniqueTexts.add(text.trim().toLowerCase());
            }

            // ─ Check isCorrect non null ─
            if (answer.isCorrect() == null) {
                context.buildConstraintViolationWithTemplate(FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL)
                        .addPropertyNode("answers[" + i + "].isCorrect")
                        .addConstraintViolation();
                valid = false;
            } else if (Boolean.TRUE.equals(answer.isCorrect())) {
                hasCorrectAnswer = true;
            }
        }


        // ─── Vérif 3 : au moins une correcte ──────────────────────────────────────
        if (!hasCorrectAnswer) {
            globalMessages.add(FieldConstraint.Question.AT_LEAST_ONE_CORRECT_ANSWER_REQUIRED);
            valid = false;
        }

        // Vérif 4 : unicité des réponses valides → erreur globale
        long validTexts = answers.stream()
                .map(ClassicAnswerUpsertDTO::text)
                .filter(text -> text != null && !text.trim().isEmpty())
                .count();

        if (uniqueTexts.size() != validTexts) {
            globalMessages.add(FieldConstraint.Question.ONLY_UNIQUE_ANSWERS_ALLOWED);
            valid = false;
        }

        // Ajout d’UNE violation globale regroupant tous les messages globaux concaténés
        if (!globalMessages.isEmpty()) {
            String combinedMessage = String.join(" ", globalMessages);
            context.buildConstraintViolationWithTemplate(combinedMessage)
                    .addPropertyNode("global")
                    .addConstraintViolation();
        }

        return valid;
    }
}

