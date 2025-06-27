package com.dassonville.api.repository;

import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Long> {

    /**
     * Vérifie si un niveau de difficulté existe avec le même nom (insensible à la casse).
     *
     * @param name Le nom du niveau de difficulté.
     * @return {@code true} si un niveau de difficulté existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Vérifie si un niveau de difficulté existe avec le même nom (insensible à la casse) en excluant un ID spécifique.
     *
     * @param name Le nom du niveau de difficulté.
     * @param id   L'ID du niveau de difficulté à exclure.
     * @return {@code true} si un niveau de difficulté existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);

    /**
     * Vérifie si un niveau de difficulté est valide pour un quiz donné.
     *
     * <p>
     * ⚠️ Cette méthode de vérification n'est pertinente que pour les quiz avec type de question multiple.
     * Elle permet de ne pas être trop restrictif.
     * </p>
     *
     * <p>
     * Un niveau de difficulté est considéré comme valide s'il est associé à un quiz qui n'est pas désactivé,
     * et si le niveau de difficulté a un rang défini.
     * </p>
     *
     * @param quizId       L'identifiant du quiz.
     * @param difficultyId L'identifiant du niveau de difficulté.
     * @return {@code true} si le niveau de difficulté est valide pour le quiz, sinon {@code false}.
     */
    @Query("""
        SELECT CASE WHEN difficulty.rank IS NOT NULL THEN TRUE ELSE FALSE END
        FROM DifficultyLevel difficulty
            JOIN difficulty.gameRules rule
            JOIN difficulty.questionTypes questionType
            JOIN questionType.questions question
            JOIN question.quizzes quiz
            JOIN quiz.quizType quizType
            JOIN quiz.masteryLevel masteryLevel
            JOIN quiz.theme theme
            LEFT JOIN quiz.category category
        WHERE quiz.id = :quizId
            AND difficulty.id = :difficultyId
            AND rule.disabledAt IS NULL
            AND question.disabledAt IS NULL
            AND quiz.disabledAt IS NULL
            AND quizType.disabledAt IS NULL
            AND masteryLevel.disabledAt IS NULL
            AND theme.disabledAt IS NULL
            AND category.disabledAt IS NULL
        """)
    boolean existsValidDifficultyForQuiz(long quizId, long difficultyId);

    /**
     * Récupère les projections publiques des niveaux de difficulté actifs pour un quiz donné, triés par ordre d'affichage.
     *
     * <p>
     * Pour les cas où plusieurs types de questions sont présents, seuls les niveaux de difficulté avec un rang défini sont retournés.
     * Ceci permet de s'assurer que les niveaux de difficulté sont pertinents pour le quiz en question en excluant le label {@code SPÉCIAL} qui n'est pas pertinent pour les quiz avec plusieurs types de questions.
     * </p>
     *
     * @param quizId L'identifiant du quiz pour lequel récupérer les niveaux de difficulté.
     * @return La liste des projections publiques des niveaux de difficulté actifs.
     */
    @Query("""
            SELECT difficulty.id AS id,
                   difficulty.name AS name,
                   difficulty.label AS label,
                   difficulty.description AS description,
                   difficulty.createdAt AS createdAt
            FROM DifficultyLevel difficulty
                JOIN difficulty.gameRules rule
                JOIN difficulty.questionTypes questionType
                JOIN questionType.questions question
                JOIN question.quizzes quiz
                JOIN quiz.masteryLevel masteryLevel
                JOIN quiz.quizType quizType
                JOIN quiz.theme theme
                LEFT JOIN quiz.category category
            WHERE quiz.id = :quizId
                AND difficulty.disabledAt IS NULL
                AND rule.disabledAt IS NULL
                AND question.disabledAt IS NULL
                AND quizType.disabledAt IS NULL
                AND quiz.disabledAt IS NULL
                AND masteryLevel.disabledAt IS NULL
                AND theme.disabledAt IS NULL
                AND category.disabledAt IS NULL
                AND (
                    (SELECT COUNT(DISTINCT questionType2.code)
                     FROM QuestionType questionType2
                         JOIN questionType2.questions question2
                         JOIN question2.quizzes quiz2
                     WHERE quiz2.id = :quizId
                           AND question2.disabledAt IS NULL
                           AND quiz2.disabledAt IS NULL
                    ) = 1
                    OR difficulty.rank IS NOT NULL
                )
            GROUP BY difficulty.id
            ORDER BY difficulty.rank
            """)
    List<PublicDifficultyLevelProjection> findByDisabledAtIsNullOrderByRank(long quizId);

    /**
     * Récupère tous les niveaux de difficulté triés par ordre d'affichage.
     *
     * @return La liste des niveaux de difficulté triés.
     */
    List<DifficultyLevel> findAllByOrderByRank();
}
