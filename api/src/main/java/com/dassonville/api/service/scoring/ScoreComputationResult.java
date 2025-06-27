package com.dassonville.api.service.scoring;

public record ScoreComputationResult(
        boolean isInTime,
        int score,
        String message
) {
}
