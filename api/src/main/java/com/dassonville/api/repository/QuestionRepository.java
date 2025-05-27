package com.dassonville.api.repository;


import com.dassonville.api.model.Question;
import com.dassonville.api.projection.QuestionForPlayProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Vérifie si une question existe avec le même texte (insensible à la casse) pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @param text   Le texte de la question.
     * @return {@code true} si une question existe, sinon {@code false}.
     */
    boolean existsByQuizIdAndTextIgnoreCase(long quizId, String text);

    /**
     * Vérifie si une question existe avec le même texte (insensible à la casse) pour un quiz donné, en excluant un ID spécifique.
     *
     * @param quizId L'ID du quiz.
     * @param text   Le texte de la question.
     * @param id     L'ID de la question à exclure.
     * @return {@code true} si une question existe, sinon {@code false}.
     */
    boolean existsByQuizIdAndTextIgnoreCaseAndIdNot(long quizId, String text, long id);

    /**
     * Vérifie si des questions existent pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return {@code true} si des questions existent, sinon {@code false}.
     */
    boolean existsByQuizId(long quizId);

    /**
     * Vérifie si une question active existe selon un ID spécifique et pour un quiz donné.
     *
     * @param id     L'ID de la question.
     * @param quizId L'ID du quiz.
     * @return {@code true} si une question active existe, sinon {@code false}.
     */
    boolean existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(long id, long quizId);

    /**
     * Compte le nombre de questions actives pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return Le nombre de questions actives.
     */
    int countByQuizIdAndDisabledAtIsNull(long quizId);

    /**
     * Récupère les projections des questions actives et leurs réponses actives pour un quiz donné.
     *
     * @param quizId L'ID du quiz.
     * @return La liste des projections des questions actives.
     */
    List<QuestionForPlayProjection> findByQuizIdAndDisabledAtIsNullAndAnswersDisabledAtIsNull(long quizId);
}
