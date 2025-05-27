package com.dassonville.api.projection;

import java.time.LocalDateTime;

public interface PublicDifficultyLevelProjection {
    Long getId();
    String getName();
    Byte getAnswerOptionsCount();
    Short getTimerSeconds();
    Integer getPointsPerQuestion();
    LocalDateTime getCreatedAt();
}
