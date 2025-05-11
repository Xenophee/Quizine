package com.dassonville.api.dto;

import java.util.List;

public record QuizzesByThemeAdminDTO(
        long id,
        String name,
        List<QuizActiveAdminDTO> quizzes
) {
}
