package com.dassonville.api.dto;

import java.util.List;

public record CheckAnswerTextsRequestDTO(
        List<String> submittedTexts
) {
}
