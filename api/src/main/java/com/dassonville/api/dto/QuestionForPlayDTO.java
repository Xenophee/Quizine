package com.dassonville.api.dto;

import java.util.List;

public record QuestionForPlayDTO(
        long id,
        String text,
        List<AnswerForPlayDTO> answers
) {
}

