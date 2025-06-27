package com.dassonville.api.repository;

import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.projection.PublicThemeProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    /**
     * Vérifie si un thème existe avec le même nom (insensible à la casse).
     *
     * @param name Le nom du thème.
     * @return {@code true} si un thème existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Vérifie si un thème existe avec le même nom (insensible à la casse) en excluant un ID spécifique.
     *
     * @param name Le nom du thème.
     * @param id   L'ID du thème à exclure.
     * @return {@code true} si un thème existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);

    /**
     * Vérifie si un thème existe avec des quiz associés.
     *
     * @param id L'ID du thème.
     * @return {@code true} si un thème existe avec des quiz, sinon {@code false}.
     */
    boolean existsByIdAndQuizzesIsNotEmpty(long id);

    /**
     * Vérifie si un thème est actif (non désactivé) pour une liste d'IDs.
     *
     * @param ids La liste des IDs des thèmes.
     * @return {@code true} si au moins un thème actif existe, sinon {@code false}.
     */
    boolean existsByDisabledAtIsNullAndIdIn(List<Long> ids);

    /**
     * Compte le nombre de quiz actifs pour un thème donné.
     *
     * @param id L'ID du thème.
     * @return Le nombre de quiz actifs.
     */
    @Query("""
            SELECT COUNT(quiz)
            FROM Quiz quiz
                JOIN quiz.category category
                JOIN quiz.masteryLevel masteryLevel
                JOIN quiz.quizType quizType
            WHERE quiz.theme.id = :id
                AND quiz.disabledAt IS NULL
                AND quizType.disabledAt IS NULL
                AND category.disabledAt IS NULL
                AND masteryLevel.disabledAt IS NULL
            """)
    int countByIdAndQuizzesDisabledAtIsNull(long id);

    /**
     * Récupère les projections publiques des thèmes actifs, en plaçant le thème par défaut en dernier.
     *
     * @return La liste des projections publiques des thèmes actifs, avec le thème par défaut en dernier.
     */
    @Query("""
            SELECT theme FROM Theme theme
            WHERE theme.disabledAt IS NULL
            ORDER BY CASE WHEN theme.isDefault = true THEN 1 ELSE 0 END, theme.name
            """)
    List<PublicThemeProjection> findByDisabledAtIsNullOrderByNameWithDefaultLast();

    /**
     * Récupère les projections des IDs et noms de tous les thèmes, triés par nom.
     *
     * @return La liste des projections des IDs et noms des thèmes.
     */
    List<IdAndNameProjection> findAllByOrderByName();

    /**
     * Récupère tous les thèmes avec leurs catégories, triés par nom de thème et de catégorie.
     *
     * @return La liste des thèmes avec leurs catégories. Le thème par défaut est placé en dernier.
     */
    @EntityGraph(attributePaths = {"categories"})
    @Query("""
            SELECT theme FROM Theme theme
            LEFT JOIN theme.categories category
            ORDER BY CASE WHEN theme.isDefault = true THEN 1 ELSE 0 END, theme.name, category.name
            """)
    List<Theme> findAllByOrderByNameAndCategoryName();
}
