package com.dassonville.api.repository;

import com.dassonville.api.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    boolean existsByName(String name);

}
