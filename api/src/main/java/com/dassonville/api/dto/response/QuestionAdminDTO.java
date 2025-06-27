package com.dassonville.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionAdminDTO(
        long id,
        String text,
        String answerExplanation,
        Boolean answerIfTrueFalse,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        List<AnswerAdminDTO> answers
) {
}
