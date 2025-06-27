package com.dassonville.api.dto.response;

public record QuizPublicDetailsDTO(
        long id,
        String title,
        String description,
        String quizType,
        String masteryLevel,
        boolean isNew,
        int numberOfQuestions,
        String theme,
        String category

) {
}
