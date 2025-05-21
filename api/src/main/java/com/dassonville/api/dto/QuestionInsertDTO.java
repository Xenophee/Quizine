package com.dassonville.api.dto;

import com.dassonville.api.validation.annotation.ValidMinAnswersPerQuestion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@ValidMinAnswersPerQuestion
public record QuestionInsertDTO(
        @NotBlank(message = "Veuillez saisir le contenu de la question.")
        @Size(max = 300, message = "Le contenu de la question ne doit pas dépasser 300 caractères.")
        String text,

        @Valid
        List<AnswerUpsertDTO> answers
) {
}
