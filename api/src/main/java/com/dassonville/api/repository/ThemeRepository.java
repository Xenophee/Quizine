package com.dassonville.api.repository;

import com.dassonville.api.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    boolean existsByName(String name);

    List<Theme> findByDisabledAtIsNull();

}
