package com.dassonville.api.integration;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Category;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.service.CategoryService;
import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@DisplayName("IT - Service : Catégorie")
public class CategoryServiceIT {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Nested
    @DisplayName("Récupération de catégories")
    class GettingCategories {

        @Test
        @DisplayName("Succès - Récupérer toutes les catégories selon un thème par ordre alphabétique")
        public void shouldGetAllCategoriesByTheme() {
            // Given
            long themeId = 1L;

            // When
            List<IdAndNameProjection> categories = categoryService.findAllByTheme(themeId);

            // Then
            assertThat(categories).hasSize(3);
            assertThat(categories.getFirst().getId()).isEqualTo(14L);
            assertThat(categories.getFirst().getName()).isEqualTo("Mythologie & Religion");
        }

        @Test
        @DisplayName("Erreur - Récupérer toutes les catégories selon un thème - thème non trouvé")
        public void shouldFailToGetAllCategoriesByTheme_WhenNonExistingTheme() {
            // Given
            long themeId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.findAllByTheme(themeId));
        }

        @Test
        @DisplayName("Succès - Récupérer une catégorie par son ID")
        public void shouldGetCategory_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            CategoryAdminDTO category = categoryService.findById(idToFind);

            // Then
            assertThat(category.name()).isEqualTo("Peinture");
        }

        @Test
        @DisplayName("Erreur - Récupérer une catégorie par son ID - Catégorie non trouvée")
        public void shouldFailToGetCategory_WhenNonExistingId() {
            // Given
            long idToFind = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.findById(idToFind));
        }
    }


    @Nested
    @DisplayName("Création de catégories")
    class CreatingCategories {

        @Test
        @DisplayName("Succès")
        public void shouldCreateCategory() {
            // Given
            CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO(" catégorie", " description");

            // When
            CategoryAdminDTO createdCategory = categoryService.create(1L, categoryToCreate);

            // Then
            Category category = categoryRepository.findById(createdCategory.id()).get();
            assertThat(category.getName()).isEqualTo("Catégorie");
            assertThat(category.getDescription()).isEqualTo("Description");
            assertThat(category.getCreatedAt()).isNotNull();
            assertThat(category.getDisabledAt()).isNull();
            assertThat(category.getTheme().getId()).isEqualTo(createdCategory.themeId());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void shouldFailToCreateCategory_WhenThemeNotFound() {
            // Given
            CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO(" catégorie", " description");

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.create(9999L, categoryToCreate));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        public void shouldFailToCreateCategory_WhenAlreadyExisting() {
            // Given
            CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO(" droit civil", "");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> categoryService.create(1L, categoryToCreate));
        }
    }


    @Nested
    @DisplayName("Mettre à jour une catégorie")
    class UpdatingCategories {

        @Test
        @DisplayName("Succès")
        public void shouldUpdate_WhenExistingCategory() {
            // Given
            long idToUpdate = 9L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO(" catégorie", " description");

            // When
            CategoryAdminDTO updatedCategory = categoryService.update(idToUpdate, categoryToUpdate);

            // Then
            Category category = categoryRepository.findById(updatedCategory.id()).get();
            assertThat(category.getName()).isEqualTo("Catégorie");
            assertThat(category.getDescription()).isEqualTo("Description");
            assertThat(category.getCreatedAt()).isNotNull();
            assertThat(category.getUpdatedAt()).isNotNull();
            assertThat(category.getTheme().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void shouldFailToUpdate_WhenNonExistingCategory() {
            // Given
            long idToUpdate = 9999L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO(" catégorie", " description");

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.update(idToUpdate, categoryToUpdate));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        public void shouldFailToUpdate_WhenCategoryWithExistingName() {
            // Given
            long idToUpdate = 5L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO(" peinture", "");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> categoryService.update(idToUpdate, categoryToUpdate));
        }
    }


    @Nested
    @DisplayName("Suppression de catégories")
    class DeletingCategories {

        @Test
        @DisplayName("Succès")
        public void shouldDelete_WhenExistingCategory() {
            // Given
            long idToDelete = 1L;

            // When
            categoryService.delete(idToDelete);

            // Then
            assertThat(categoryRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void shouldFailToDelete_WhenNonExistingCategory() {
            // Given
            long idToDelete = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.delete(idToDelete));
        }
    }


    @Nested
    @DisplayName("Désactivation / réactivation de catégories")
    class DisablingCategories {

        @Test
        @Transactional
        @DisplayName("Succès - Désactivation")
        public void shouldDisable_WhenExistingCategory() {
            // Given
            long idToDisable = 2L;

            // When
            categoryService.updateVisibility(idToDisable, false);

            // Then
            Category category = categoryRepository.findById(idToDisable).get();
            assertThat(category.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Réactivation")
        public void shouldEnable_WhenDisabledCategory() {
            // Given
            long idToEnable = 1L;

            // When
            categoryService.updateVisibility(idToEnable, true);

            // Then
            Category category = categoryRepository.findById(idToEnable).get();
            assertThat(category.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void shouldFailToDisable_WhenNonExistingCategory() {
            // Given
            long idToDisable = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.updateVisibility(idToDisable, true));
        }
    }
}
