package com.dassonville.api.repository;

import com.dassonville.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByIdAndThemeId(long categoryId, long themeId);

    List<Category> findByThemeId(Long themeId);
}
