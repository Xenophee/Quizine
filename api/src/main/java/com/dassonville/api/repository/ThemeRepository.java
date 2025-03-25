package com.dassonville.api.repository;

import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.PublicThemeProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"categories"})
    List<Theme> findAll();

    Optional<PublicThemeProjection> findByIdAndDisabledAtIsNull(Long id);

    List<PublicThemeProjection> findByDisabledAtIsNull();

}
