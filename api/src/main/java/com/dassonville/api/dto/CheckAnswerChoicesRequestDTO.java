package com.dassonville.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CheckAnswerChoicesRequestDTO(

        List< @NotNull(message = "Les identifiants ne peuvent pas être null.")
        @Positive(message = "Les identifiants doivent être des nombres positifs.") Long> answerIds
) {
}
