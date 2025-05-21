package com.dassonville.api.dto;

import com.dassonville.api.validation.annotation.ValidNumberOfLevelForReorder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@ValidNumberOfLevelForReorder
public record ReorderRequestDTO(
        @NotEmpty(message = "La liste des identifiants ne peut pas être vide.")
        List< @NotNull(message = "Les identifiants ne peuvent pas être null.")
                @Positive(message = "Les identifiants doivent être des nombres positifs.") Long> orderedIds
) {
}
