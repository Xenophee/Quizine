package com.dassonville.api.dto;

public record QuizPublicDetailsDTO(
        long id,
        String title,
        boolean isNew,
        int numberOfQuestions,
        String category,
        String theme
) {
}
