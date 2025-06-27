package com.dassonville.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CheckClassicAnswerResultDTO(

        boolean isCorrect,
        boolean isInTime,
        int score,
        String resultMessage,
        String answerExplanation,

        @Schema(description = "Liste des réponses correctes pour la question.",
                example = "[{\"id\": 1, \"text\": \"Paris\"}]")
        List<AnswerForPlayDTO> correctAnswers

) implements CheckAnswerResultDTO {

    public static CheckClassicAnswerResultDTO withoutScore(boolean isCorrect, String answerExplanation, List<AnswerForPlayDTO> correctAnswers) {
        return new CheckClassicAnswerResultDTO(
                isCorrect,
                true,
                0,
                "Résultat non établi.",
                answerExplanation,
                correctAnswers
        );
    }

}
