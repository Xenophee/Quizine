package com.dassonville.api.dto.response;

public record QuizPublicDTO(
        long id,
        String title,
        String quizType,
        String masteryLevel,
        boolean isNew,
        int numberOfQuestions,
        String theme,
        String category
) {
}
