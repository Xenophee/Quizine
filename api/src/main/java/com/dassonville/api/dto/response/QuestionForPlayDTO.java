package com.dassonville.api.dto.response;

import java.util.List;

public record QuestionForPlayDTO(
        long id,
        String masteryLevel,
        String theme,
        String category,
        String questionType,
        String instruction,
        String text,
        List<AnswerForPlayDTO> answers
) {
}

