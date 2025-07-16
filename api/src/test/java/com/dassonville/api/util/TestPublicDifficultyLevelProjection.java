package com.dassonville.api.util;

import com.dassonville.api.projection.PublicDifficultyLevelProjection;

import java.time.LocalDateTime;

public class TestPublicDifficultyLevelProjection implements PublicDifficultyLevelProjection {
    public Long getId() { return 1L; }
    public String getName() { return "Facile"; }
    public String getLabel() { return "FACILE"; }
    public String getDescription() { return "Un niveau facile pour commencer."; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    private final LocalDateTime createdAt;

    public TestPublicDifficultyLevelProjection(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
