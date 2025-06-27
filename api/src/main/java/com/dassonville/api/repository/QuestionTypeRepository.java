package com.dassonville.api.repository;


import com.dassonville.api.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, String> {

    /**
     * Vérifie si un type de question existe avec un ID spécifique et associé à un quiz.
     *
     * @param questionTypeCode L'ID du type de question.
     * @param quizId L'ID du quiz.
     * @return {@code true} si le type de question existe pour le quiz, sinon {@code false}.
     */
    boolean existsByCodeAndQuizTypes_Quizzes_Id(String questionTypeCode, long quizId);

    /**
     * Vérifie si un type de question existe avec un ID spécifique et un niveau de difficulté associé.
     *
     * @param questionTypeCode L'ID du type de question.
     * @param difficultyLevelId L'ID du niveau de difficulté.
     * @return {@code true} si le type de question existe avec le niveau de difficulté, sinon {@code false}.
     */
    @Query("""
            SELECT CASE WHEN COUNT(type) > 0 THEN TRUE ELSE FALSE END
            FROM QuestionType type
                JOIN type.difficultyLevels difficulty
            WHERE type.code = :questionTypeCode AND difficulty.id = :difficultyLevelId
            AND difficulty.disabledAt IS NULL
            """)
    boolean existsByIdAndDifficultyLevelsId(String questionTypeCode, long difficultyLevelId);

    /**
     * Récupère les IDs des types de questions associés à un type de quiz spécifique.
     *
     * @param quizTypeCode L'ID du type de quiz.
     * @return Une liste d'IDs de types de questions associés au type de quiz.
     */
    @Query("""
            SELECT questionType.code
            FROM QuestionType questionType
                JOIN questionType.quizTypes quizType
            WHERE quizType.code = :quizTypeCode
            """)
    List<String> findIdsByQuizTypeCode(String quizTypeCode);
}
