package com.dassonville.api.repository;

import com.dassonville.api.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    /**
     * Vérifie si une réponse existe avec le même texte (insensible à la casse) pour une question donnée.
     *
     * @param text       Le texte de la réponse.
     * @param questionId L'ID de la question associée.
     * @return {@code true} si une réponse existe, sinon {@code false}.
     */
    boolean existsByTextIgnoreCaseAndQuestionId(String text, long questionId);

    /**
     * Vérifie si une réponse existe avec le même texte (insensible à la casse) en excluant un ID spécifique.
     *
     * @param text Le texte de la réponse.
     * @param id   L'ID de la réponse à exclure.
     * @return {@code true} si une réponse existe, sinon {@code false}.
     */
    boolean existsByTextIgnoreCaseAndIdNot(String text, long id);

    /**
     * Vérifie si des réponses existent pour une question donnée.
     *
     * @param questionId L'ID de la question.
     * @return {@code true} si des réponses existent, sinon {@code false}.
     */
    boolean existsByQuestionId(long questionId);

    /**
     * Vérifie si une réponse correcte active existe pour une question donnée, en excluant une réponse spécifique.
     *
     * @param questionId L'ID de la question.
     * @param id         L'ID de la réponse à exclure.
     * @return {@code true} si une réponse correcte et active existe, sinon {@code false}.
     */
    boolean existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(long questionId, long id);

    /**
     * Compte le nombre de réponses actives pour une question donnée à partir d'une liste d'IDs fournie.
     *
     * @param ids        La liste des IDs des réponses.
     * @param questionId L'ID de la question.
     * @return Le nombre de réponses actives correspondant aux IDs et à la question spécifiée.
     */
    @Query("""
                SELECT COUNT(id) FROM Answer
                WHERE id IN :ids AND question.id = :questionId AND disabledAt IS NULL AND question.disabledAt IS NULL
            """)
    int countActiveValidAnswers(@Param("ids") List<Long> ids, @Param("questionId") long questionId);

    /**
     * Compte le nombre de réponses actives pour une question donnée.
     *
     * @param questionId L'ID de la question.
     * @return Le nombre de réponses actives.
     */
    int countByQuestionIdAndDisabledAtIsNull(long questionId);

    /**
     * Récupère toutes les réponses correctes et actives pour une question donnée.
     *
     * @param questionId L'ID de la question.
     * @return La liste des réponses correctes et actives.
     */
    List<Answer> findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(long questionId);
}
