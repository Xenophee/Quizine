package com.dassonville.api.repository;

import com.dassonville.api.model.Quiz;
import com.dassonville.api.projection.PublicQuizProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


    /**
     * Récupère une page de projections publiques des quiz actifs.
     * Le thème et la catégorie doivent également être actifs.
     *
     * @param pageable Les informations de pagination.
     * @return Une page de projections publiques des quiz actifs.
     */
    @Query("""
            SELECT quiz.id AS id,
                   quiz.title AS title,
                   quiz.createdAt AS createdAt,
                   COUNT(CASE WHEN question.disabledAt IS NULL THEN question.id END) AS numberOfQuestions,
                   category.name AS categoryName,
                   theme.name AS themeName
            FROM Quiz quiz
            JOIN quiz.category category
            JOIN quiz.theme theme
            LEFT JOIN quiz.questions question
            WHERE quiz.disabledAt IS NULL
                 AND theme.disabledAt IS NULL
                 AND category.disabledAt IS NULL
            GROUP BY quiz.id, quiz.title, quiz.createdAt, category.name, theme.name
            """)
    Page<PublicQuizProjection> findAllByDisabledAtIsNull(Pageable pageable);

    /**
     * Récupère une page de projections publiques des quiz actifs pour des thèmes spécifiques.
     * Le thème et la catégorie doivent également être actifs.
     *
     * @param themeIds La liste des IDs des thèmes.
     * @param pageable Les informations de pagination.
     * @return Une page de projections publiques des quiz actifs.
     */
    @Query("""
            SELECT quiz.id AS id,
                   quiz.title AS title,
                   quiz.createdAt AS createdAt,
                   COUNT(CASE WHEN question.disabledAt IS NULL THEN question.id END) AS numberOfQuestions,
                   category.name AS categoryName,
                   theme.name AS themeName
            FROM Quiz quiz
            JOIN quiz.category category
            JOIN quiz.theme theme
            LEFT JOIN quiz.questions question
            WHERE quiz.disabledAt IS NULL
                AND theme.disabledAt IS NULL
                AND category.disabledAt IS NULL
                AND theme.id IN :themeIds
            GROUP BY quiz.id, quiz.title, quiz.createdAt, category.name, theme.name
            """)
    Page<PublicQuizProjection> findAllByDisabledAtIsNullAndThemeIdIn(List<Long> themeIds, Pageable pageable);


    /**
     * Vérifie si un quiz actif existe avec un ID spécifique.
     *
     * @param id L'ID du quiz.
     * @return {@code true} si un quiz actif existe, sinon {@code false}.
     */
    boolean existsByIdAndDisabledAtIsNull(long id);

    /**
     * Vérifie si un quiz existe avec le même titre (insensible à la casse).
     *
     * @param title Le titre du quiz.
     * @return {@code true} si un quiz existe, sinon {@code false}.
     */
    boolean existsByTitleIgnoreCase(String title);

    /**
     * Vérifie si un quiz existe avec le même titre (insensible à la casse) en excluant un ID spécifique.
     *
     * @param title Le titre du quiz.
     * @param id    L'ID du quiz à exclure.
     * @return {@code true} si un quiz existe, sinon {@code false}.
     */
    boolean existsByTitleIgnoreCaseAndIdNot(String title, long id);

    /**
     * Compte le nombre de quiz actifs pour un thème donné.
     *
     * @param themeId L'ID du thème.
     * @return Le nombre de quiz actifs.
     */
    int countByThemeIdAndDisabledAtIsNull(long themeId);

    /**
     * Compte le nombre de questions actives pour un quiz donné.
     *
     * @param id L'ID du quiz.
     * @return Le nombre de questions actives.
     */
    int countByIdAndQuestionsDisabledAtIsNull(long id);

    /**
     * Récupère un quiz actif par son ID. Le thème et la catégorie doivent également être actifs.
     *
     * @param id L'ID du quiz.
     * @return Un {@code Optional} contenant le quiz s'il est actif, sinon vide.
     */
    Optional<Quiz> findByIdAndDisabledAtIsNullAndThemeDisabledAtIsNullAndCategoryDisabledAtIsNull(long id);

    /**
     * Récupère tous les quiz actifs pour un thème donné.
     *
     * @param themeId L'ID du thème.
     * @return La liste des quiz actifs.
     */
    List<Quiz> findByThemeIdAndDisabledAtIsNull(long themeId);

    /**
     * Récupère une page de quiz actifs pour un thème donné.
     *
     * @param themeId  L'ID du thème.
     * @param pageable Les informations de pagination.
     * @return Une page de quiz actifs.
     */
    Page<Quiz> findByThemeIdAndDisabledAtIsNull(long themeId, Pageable pageable);

    /**
     * Récupère une page de quiz désactivés pour un thème donné.
     *
     * @param themeId  L'ID du thème.
     * @param pageable Les informations de pagination.
     * @return Une page de quiz désactivés.
     */
    Page<Quiz> findByThemeIdAndDisabledAtIsNotNull(long themeId, Pageable pageable);

    /**
     * Récupère une page de tous les quiz pour un thème donné.
     *
     * @param themeId  L'ID du thème.
     * @param pageable Les informations de pagination.
     * @return Une page de tous les quiz.
     */
    Page<Quiz> findByThemeId(long themeId, Pageable pageable);

}
