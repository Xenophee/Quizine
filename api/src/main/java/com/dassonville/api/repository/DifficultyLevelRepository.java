package com.dassonville.api.repository;

import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
     * Compte le nombre total de niveaux de difficulté.
     *
     * @return Le nombre total de niveaux de difficulté.
     */
    int countBy();

    /**
     * Récupère les projections publiques des niveaux de difficulté actifs, triés par ordre d'affichage.
     *
     * @return La liste des projections publiques des niveaux de difficulté actifs.
     */
    List<PublicDifficultyLevelProjection> findByDisabledAtIsNullOrderByDisplayOrder();

    /**
     * Récupère tous les niveaux de difficulté triés par ordre d'affichage.
     *
     * @return La liste des niveaux de difficulté triés.
     */
    List<DifficultyLevel> findAllByOrderByDisplayOrder();

    /**
     * Récupère le nombre maximum de réponses à proposer pour le niveau de difficulté de référence.
     *
     * @return Un {@code Optional} contenant le nombre maximum de réponses, sinon vide.
     */
    @Query("SELECT answerOptionsCount FROM DifficultyLevel WHERE isReference = true")
    Optional<Byte> findAnswerOptionsCountByReferenceLevel();

    /**
     * Récupère le nombre maximum de réponses à proposer pour un niveau de difficulté actif par son ID.
     *
     * @param id L'ID du niveau de difficulté.
     * @return Un {@code Optional} contenant le nombre maximum de réponses, sinon vide.
     */
    @Query("SELECT answerOptionsCount FROM DifficultyLevel WHERE id = :id AND disabledAt IS NULL")
    Optional<Byte> findAnswerOptionsCountByIdAndDisabledAtIsNull(long id);

    /**
     * Récupère l'ordre d'affichage maximum parmi tous les niveaux de difficulté.
     *
     * @return Un {@code Optional} contenant l'ordre d'affichage maximum, sinon vide.
     */
    @Query("SELECT MAX(displayOrder) FROM DifficultyLevel")
    Optional<Short> findMaxDisplayOrder();

}
