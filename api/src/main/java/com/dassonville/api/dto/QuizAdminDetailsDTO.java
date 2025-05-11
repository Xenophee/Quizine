package com.dassonville.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuizAdminDetailsDTO(
        long id,
        String title,
        boolean isVipOnly,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        long categoryId,
        long themeId,
        List<QuestionAdminDTO> questions
) {
}
