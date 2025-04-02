package com.dassonville.api.repository;

import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<PublicDifficultyLevelProjection> findByDisabledAtIsNull();
}
