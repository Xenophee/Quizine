package com.dassonville.api.projection;

import java.time.LocalDate;

public interface PublicDifficultyLevelProjection {
    Long getId();
    String getName();
    Byte getMaxResponses();
    Short getTimerSeconds();
    Integer getPointsPerQuestion();
    LocalDate getCreatedAt();
}
