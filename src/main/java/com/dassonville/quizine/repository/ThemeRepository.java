package com.dassonville.quizine.repository;

import com.dassonville.quizine.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    boolean existsByName(String name);

    Optional<Theme> findByName(String name);

}
