package com.dassonville.api.repository;

import com.dassonville.api.model.Category;
import com.dassonville.api.projection.IdAndNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Vérifie si une catégorie existe avec le même nom (insensible à la casse).
     *
     * @param name Le nom de la catégorie.
     * @return {@code true} si une catégorie existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Vérifie si une catégorie existe avec le même nom (insensible à la casse) en excluant un ID spécifique.
     *
     * @param name Le nom de la catégorie.
     * @param id   L'ID de la catégorie à exclure.
     * @return {@code true} si une catégorie existe, sinon {@code false}.
     */
    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);

    /**
     * Vérifie si une catégorie existe pour un thème donné.
     *
     * @param categoryId L'ID de la catégorie.
     * @param themeId    L'ID du thème.
     * @return {@code true} si une catégorie existe pour le thème, sinon {@code false}.
     */
    boolean existsByIdAndThemeId(long categoryId, long themeId);

    /**
     * Récupère les projections des IDs et noms de toutes les catégories pour un thème donné, triées par nom.
     *
     * @param themeId L'ID du thème.
     * @return La liste des projections des IDs et noms de catégories.
     */
    List<IdAndNameProjection> findAllByThemeIdOrderByName(long themeId);
}
