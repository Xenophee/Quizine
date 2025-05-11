package com.dassonville.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionAdminDTO(
        long id,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime disabledAt,
        List<AnswerAdminDTO> answers
) {
}
