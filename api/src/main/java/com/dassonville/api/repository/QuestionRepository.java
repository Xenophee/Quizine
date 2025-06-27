package com.dassonville.api.repository;


import com.dassonville.api.model.Question;
import com.dassonville.api.projection.QuestionForPlayProjection;
import com.dassonville.api.projection.TrueFalseQuestionExplanationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Vérifie si le type de question spécifié existe pour une question donnée.
     *
     * @param id             L'ID de la question.
     * @param questionTypeCode L'ID du type de question.
     * @return {@code true} si le type de question existe pour l'ID donné, sinon {@code false}.
     */
    boolean existsByIdAndQuestionTypeCode(long id, String questionTypeCode);

    /**
     * Vérifie si une question existe avec le même texte (insensible à la casse) pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @param text   Le texte de la question.
     * @return {@code true} si une question existe, sinon {@code false}.
     */
    boolean existsByQuizzesIdAndTextIgnoreCase(long quizId, String text);

    /**
     * Vérifie si une question existe avec le même texte (insensible à la casse) pour un quiz donné, en excluant un ID spécifique.
     *
     * @param quizId L'ID du quiz.
     * @param text   Le texte de la question.
     * @param id     L'ID de la question à exclure.
     * @return {@code true} si une question existe, sinon {@code false}.
     */
    boolean existsByQuizzesIdAndTextIgnoreCaseAndIdNot(long quizId, String text, long id);

    /**
     * Vérifie si des questions existent pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return {@code true} si des questions existent, sinon {@code false}.
     */
    boolean existsByQuizzesId(long quizId);

    /**
     * Vérifie si une question active existe selon un ID spécifique et pour un quiz donné.
     *
     * @param id     L'ID de la question.
     * @param quizId L'ID du quiz.
     * @return {@code true} si une question active existe, sinon {@code false}.
     */
    @Query("""
            SELECT CASE WHEN COUNT(question) > 0 THEN TRUE ELSE FALSE END
            FROM Question question
                JOIN question.quizzes quizzes
                JOIN quizzes.quizType quizType
                JOIN quizzes.masteryLevel masteryLevel
                JOIN quizzes.theme theme
                LEFT JOIN quizzes.category category
            WHERE question.id = :id AND question.disabledAt IS NULL
                AND quizzes.id = :quizId AND quizzes.disabledAt IS NULL
                AND quizzes.quizType.disabledAt IS NULL
                AND quizzes.masteryLevel.disabledAt IS NULL
                AND quizzes.theme.disabledAt IS NULL
                AND quizzes.category.disabledAt IS NULL
            """)
    boolean existsByIdAndDisabledAtIsNullEverywhere(long id, long quizId);

    /**
     * Compte le nombre de questions actives pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return Le nombre de questions actives.
     */
    int countByQuizzesIdAndDisabledAtIsNull(long quizId);

    /**
     * Récupère les projections des questions actives et leurs réponses actives pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return La liste des projections des questions actives.
     */
    List<QuestionForPlayProjection> findByQuizzesIdAndDisabledAtIsNullAndClassicAnswersDisabledAtIsNull(long quizId);

    /**
     * Récupère l'explication de la réponse d'une question par son ID.
     *
     * @param id L'ID de la question.
     * @return Un {@code Optional} contenant l'explication de la question si elle existe, sinon une option vide.
     */
    @Query("SELECT answerExplanation FROM Question WHERE id = :id")
    Optional<String> findAnswerExplanationById(long id);

    /**
     * Récupère l'explication et la réponse d'une question de type Vrai/Faux par son ID.
     *
     * @param id L'ID de la question.
     * @return Un {@code Optional} contenant l'explication et la réponse si elles existent, sinon une option vide.
     */
    @Query("""
            SELECT answerExplanation AS answerExplanation,
                   answerIfTrueFalse AS answerIfTrueFalse
            FROM Question
            WHERE id = :id
            """)
    Optional<TrueFalseQuestionExplanationProjection> findTrueFalseQuestionAnswerAndExplanationById(long id);
}
