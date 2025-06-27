package com.dassonville.api.service.scoring;

import com.dassonville.api.constant.GameType;

public record ScoreComputationContext(
        GameType gameType,
        long quizId,
        long difficultyLevelId,
        boolean isCorrect,
        boolean isTimerEnabled,
        boolean isPenaltiesEnabled,
        Integer timeSpentInSeconds
) {
}
