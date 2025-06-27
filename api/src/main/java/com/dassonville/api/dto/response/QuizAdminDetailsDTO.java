package com.dassonville.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record QuizAdminDetailsDTO(
        long id,
        String quizType,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        long masteryLevelId,
        long themeId,
        long categoryId,
        List<QuestionAdminDTO> questions
) {
}
