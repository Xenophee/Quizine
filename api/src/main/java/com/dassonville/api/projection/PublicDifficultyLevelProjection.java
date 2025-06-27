package com.dassonville.api.projection;

import java.time.LocalDateTime;

public interface PublicDifficultyLevelProjection {
    Long getId();
    String getName();
    String getLabel();
    String getDescription();
    LocalDateTime getCreatedAt();
}
