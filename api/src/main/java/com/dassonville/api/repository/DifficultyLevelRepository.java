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

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);

    int countBy();

    List<PublicDifficultyLevelProjection> findByDisabledAtIsNullOrderByDisplayOrder();

    List<DifficultyLevel> findAllByOrderByDisplayOrder();

    @Query("SELECT maxAnswers FROM DifficultyLevel WHERE isReference = true")
    Optional<Byte> findReferenceLevelMaxAnswers();

    @Query("SELECT MAX(displayOrder) FROM DifficultyLevel")
    Optional<Short> findMaxDisplayOrder();

}
