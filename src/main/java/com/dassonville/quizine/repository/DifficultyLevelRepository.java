package com.dassonville.quizine.repository;

import com.dassonville.quizine.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Long> {

    Optional<DifficultyLevel> findByName(String name);
}
