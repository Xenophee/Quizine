package com.dassonville.api.repository;


import com.dassonville.api.model.GameRule;
import com.dassonville.api.projection.AnswerOptionsRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRuleRepository extends JpaRepository<GameRule, Long> {


    /**
     * Selon l'identifiant du type de question, retourne le nombre maximal d'options de réponse pour ce type de question en interrogeant les règles disponibles.
     *
     * @param questionTypeId L'identifiant du type de question.
     * @return un {@code Optional} contenant le nombre maximal d'options de réponse pour le type de question spécifié.
     */
    @Query("SELECT MAX(answerOptionsCount) FROM GameRule WHERE questionType.code = :questionTypeId")
    Optional<Byte> findMaxAnswerOptionsCountByQuestionTypeId(String questionTypeId);


    /**
     * Récupère, pour chaque type de question fourni, la meilleure règle applicable en fonction d'un niveau de difficulté donné.
     * <p>
     * Cette requête retourne un seul enregistrement par {@code id_question_type}, en sélectionnant la règle qui correspond
     * le mieux selon plusieurs critères :
     * <ul>
     *     <li>La règle ne doit pas être désactivée ({@code disabled_at IS NULL}).</li>
     *     <li>Les niveaux de difficulté associés (actuel et sélectionné) doivent être actifs.</li>
     *     <li>La règle doit être temporellement valide (entre {@code starts_at} et {@code ends_at} ou sans contrainte).</li>
     *     <li>La distance de rang entre les niveaux de difficulté est utilisée pour prioriser les règles ; un rang égal est privilégié.</li>
     *     <li>En cas d’égalité, la priorité définie dans la règle est utilisée comme critère de départage.</li>
     *     <li>Les règles associées à un niveau de difficulté marqué "SPÉCIAL" peuvent être incluses ou exclues selon la valeur du paramètre {@code includeSpecial}.</li>
     * </ul>
     *
     * @param questionTypeIds liste des identifiants de types de question pour lesquels récupérer les règles.
     * @param difficultyId identifiant du niveau de difficulté sélectionné.
     * @param includeSpecial si {@code true}, les règles associées à un niveau de difficulté "SPÉCIAL" sont incluses ;
     *                       sinon, elles sont exclues.
     * @return une liste de projections {@link AnswerOptionsRuleProjection}, contenant pour chaque type de question
     * la meilleure règle disponible (nombre d’options de réponse + ID du type de question).
     */
    @Query(value = """
                SELECT DISTINCT ON (rule.question_type_code)
                       rule.answer_options_count AS answerOptionsCount,
                       rule.question_type_code AS questionTypeId
                FROM game_rules rule
                JOIN difficulty_levels difficulty ON rule.difficulty_level_id = difficulty.id
                JOIN difficulty_levels selected_difficulty ON selected_difficulty.id = :difficultyId
                WHERE rule.question_type_code IN (:questionTypeIds)
                      AND rule.disabled_at IS NULL
                      AND difficulty.disabled_at IS NULL
                      AND selected_difficulty.disabled_at IS NULL
                      AND (:includeSpecial = TRUE OR difficulty.label != 'SPÉCIAL')
                      AND (rule.starts_at IS NULL OR rule.starts_at <= CURRENT_TIMESTAMP)
                      AND (rule.ends_at IS NULL OR rule.ends_at >= CURRENT_TIMESTAMP)
                ORDER BY
                      rule.question_type_code,
                      CASE WHEN COALESCE(difficulty.rank, 0) <= COALESCE(selected_difficulty.rank, 0) THEN 0 ELSE 1 END,
                      ABS(COALESCE(difficulty.rank, 0) - COALESCE(selected_difficulty.rank, 0)),
                      rule.priority DESC
            """, nativeQuery = true)
    List<AnswerOptionsRuleProjection> findAnswerOptionsBestMatchingRulesByQuestionTypeIds(List<String> questionTypeIds, long difficultyId, boolean includeSpecial);


    /**
     * Recherche la meilleure règle de jeu applicable pour un type de question donné et un niveau de difficulté sélectionné.
     * <p>
     * Cette requête retourne au plus une règle, considérée comme la plus pertinente selon un ensemble de critères hiérarchisés :
     * <ul>
     *     <li>La règle et les niveaux de difficulté associés doivent être actifs ({@code disabled_at IS NULL}).</li>
     *     <li>La règle doit être valide temporellement (entre {@code starts_at} et {@code ends_at}, ou sans contrainte de date).</li>
     *     <li>Si {@code includeSpecial} est {@code false}, les règles associées au niveau de difficulté "SPÉCIAL" sont exclues.</li>
     *     <li>La proximité du rang entre le niveau de difficulté de la règle et celui sélectionné est utilisée pour prioriser :
     *         un rang identique est préféré.</li>
     *     <li>En cas d’égalité, la priorité définie dans la règle est utilisée pour départager les résultats.</li>
     * </ul>
     *
     * @param questionTypeId l’identifiant du type de question concerné.
     * @param difficultyId l’identifiant du niveau de difficulté sélectionné.
     * @param includeSpecial si {@code true}, les règles liées à un niveau "SPÉCIAL" sont incluses ; sinon, elles sont exclues.
     * @return un {@link Optional} contenant la règle de jeu la plus appropriée si elle existe ; sinon, un {@link Optional} vide.
     */
    @Query(value = """
                SELECT rule.*
                FROM game_rules rule
                    JOIN difficulty_levels difficulty ON rule.difficulty_level_id = difficulty.id
                    JOIN difficulty_levels selected_difficulty ON selected_difficulty.id = :difficultyId
                WHERE rule.question_type_code = :questionTypeId
                    AND rule.disabled_at IS NULL
                    AND difficulty.disabled_at IS NULL
                    AND selected_difficulty.disabled_at IS NULL
                    AND (:includeSpecial = TRUE OR difficulty.label != 'SPÉCIAL')
                    AND (rule.starts_at IS NULL OR rule.starts_at <= CURRENT_TIMESTAMP)
                    AND (rule.ends_at IS NULL OR rule.ends_at >= CURRENT_TIMESTAMP)
                ORDER BY
                    CASE WHEN COALESCE(difficulty.rank, 0) = COALESCE(selected_difficulty.rank, 0) THEN 0 ELSE 1 END,
                    ABS(COALESCE(difficulty.rank, 0) - COALESCE(selected_difficulty.rank, 0)),
                    rule.priority DESC
                LIMIT 1
            """, nativeQuery = true)
    Optional<GameRule> findBestMatchingRule(String questionTypeId, long difficultyId, boolean includeSpecial);
}
