package com.dassonville.api.service;

import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.model.GameRule;
import com.dassonville.api.repository.GameRuleRepository;
import com.dassonville.api.service.scoring.ScoreComputationContext;
import com.dassonville.api.service.scoring.ScoreComputationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreComputationService {

    private final GameRuleRepository gameRuleRepository;


    /**
     * Calcule le score basé sur le contexte de la réponse et les règles de jeu applicables.
     *
     * @param context Le contexte contenant les informations nécessaires pour le calcul du score.
     * @return Un objet {@link ScoreComputationResult} contenant le résultat du calcul du score.
     * @throws InvalidStateException si aucune règle de jeu applicable n'est trouvée.
     */
    public ScoreComputationResult computeScore(ScoreComputationContext context, boolean isQuizSoloQuestionType) {

        GameRule rule = gameRuleRepository
                .findBestMatchingRule(context.gameType().getMainType(), context.difficultyLevelId(), isQuizSoloQuestionType)
                .orElseThrow(() -> {
                    log.error("Aucune règle de jeu applicable trouvée pour le niveau de difficulté {} et le type de question {}.",
                            context.difficultyLevelId(), context.gameType().getMainType());
                    return new InvalidStateException(ErrorCode.INTERNAL_ERROR);
                });

        log.debug("Règles de jeu récupérées pour le type de question {} et le niveau de difficulté {} : {}",
                context.gameType().getMainType(), context.difficultyLevelId(), rule.getId());


        boolean isInTime = isTimeValid(context, rule);
        int baseScore = calculateBaseScore(context, rule, isInTime);
        int finalScore = applyPenalty(context, rule, isInTime, baseScore);

        String message = generateResultMessage(context, isInTime);

        return new ScoreComputationResult(isInTime, finalScore, message);
    }


    /**
     * Calcule le score de base en fonction du contexte et de la règle de jeu.
     *
     * @param context  Le contexte contenant les informations nécessaires pour le calcul du score.
     * @param rule     La règle de jeu applicable.
     * @param isInTime Indique si la réponse a été donnée dans le temps imparti.
     * @return Le score de base calculé.
     */
    private int calculateBaseScore(ScoreComputationContext context, GameRule rule, boolean isInTime) {

        if (!context.isCorrect()) return 0;

        int score = rule.getPointsPerGoodAnswer();

        if (context.isTimerEnabled() && isInTime) {
            score = Math.round(score * rule.getPointsTimerMultiplier());
        }

        return score;
    }

    /**
     * Vérifie si le temps passé pour répondre est valide par rapport à la règle de jeu.
     *
     * @param context Le contexte contenant le temps passé en secondes.
     * @param rule    La règle de jeu contenant la limite de temps.
     * @return {@code true} si le temps est valide, {@code false} sinon.
     */
    private boolean isTimeValid(ScoreComputationContext context, GameRule rule) {
        return Optional.ofNullable(context.timeSpentInSeconds())
                .map(time -> time <= rule.getTimerSeconds())
                .orElse(false);
    }

    /**
     * Si l'option est activée, applique une pénalité si la réponse est incorrecte ou si le temps est dépassé.
     * Si la pénalité est appliquée, retourne un score négatif.
     * Sinon, applique un multiplicateur de points.
     *
     * @param context  Le contexte contenant les informations nécessaires pour le calcul du score.
     * @param rule     La règle de jeu applicable.
     * @param isInTime Indique si la réponse a été donnée dans le temps imparti.
     * @param score    Le score de base calculé.
     * @return Le score ajusté après application des pénalités ou multiplicateurs.
     */
    private int applyPenalty(ScoreComputationContext context, GameRule rule, boolean isInTime, int score) {

        if (!context.isPenaltiesEnabled()) return score;

        boolean applyPenalty = !isInTime || !context.isCorrect();

        if (applyPenalty) {
            log.info("Pénalité appliquée : -{}", rule.getPointsPenaltyPerWrongAnswer());
            return -rule.getPointsPenaltyPerWrongAnswer();
        }

        int adjustedScore = Math.round(score * rule.getPointsPenaltyMultiplier());
        log.info("Multiplicateur appliqué : {} * {} = {}", score, rule.getPointsPenaltyMultiplier(), adjustedScore);
        return adjustedScore;
    }

    /**
     * Génère un message de résultat basé sur le contexte de la réponse et si elle a été donnée dans le temps imparti.
     *
     * @param context  Le contexte de la réponse.
     * @param isInTime Indique si la réponse a été donnée dans le temps imparti.
     * @return Un message indiquant si la réponse est correcte ou incorrecte, et si elle a été donnée dans le temps.
     */
    private String generateResultMessage(ScoreComputationContext context, boolean isInTime) {

        if (!isInTime) return context.isCorrect() ? "Temps dépassé. Pas de points attribués." : "Temps dépassé.";

        return context.isCorrect() ? "Bonne réponse !" : "Mauvaise réponse.";
    }
}

