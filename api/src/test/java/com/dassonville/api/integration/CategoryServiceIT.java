package com.dassonville.api.integration;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Category;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.service.CategoryService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.util.StringUtils.capitalize;


@SpringBootTest
@DisplayName("IT - CategoryService")
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
        @DisplayName("Récupérer une catégorie par son ID")
        public void shouldGetCategory_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            CategoryAdminDTO category = categoryService.findById(idToFind);

            // Then
            assertThat(category.name()).isEqualTo("Peinture");
        }

        @Test
        @DisplayName("Récupérer une catégorie par son ID - Catégorie non trouvée")
        public void shouldFailToGetCategory_WhenNonExistingId() {
            // Given
            long idToFind = 13L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.findById(idToFind));
        }
    }


    @Nested
    @DisplayName("Création de catégories")
    class CreatingCategories {

        @Test
        @DisplayName("Créer une catégorie inexistante")
        public void shouldCreateCategory() {
            // Given
            CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO("catégorie", "description", 1);

            // When
            CategoryAdminDTO createdCategory = categoryService.create(categoryToCreate);

            // Then
            Category category = categoryRepository.findById(createdCategory.id()).get();
            assertThat(category.getName()).isEqualTo(capitalize(categoryToCreate.name()));
            assertThat(category.getCreatedAt()).isNotNull();
            assertThat(category.getDisabledAt()).isNull();
            assertThat(category.getTheme().getId()).isEqualTo(createdCategory.themeId());
        }

        @Test
        @DisplayName("Créer une catégorie déjà existante")
        public void shouldFailToCreateCategory_WhenAlreadyExisting() {
            // Given
            CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO("Droit civil", "", 2);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> categoryService.create(categoryToCreate));
        }
    }


    @Nested
    @DisplayName("Mise à jour de catégories")
    class UpdatingCategories {

        @Test
        @DisplayName("Mettre à jour une catégorie existante")
        public void shouldUpdate_WhenExistingCategory() {
            // Given
            long idToUpdate = 1L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO("catégorie", "description", 1);

            // When
            CategoryAdminDTO updatedCategory = categoryService.update(idToUpdate, categoryToUpdate);

            // Then
            Category category = categoryRepository.findById(updatedCategory.id()).get();
            assertThat(category.getName()).isEqualTo(capitalize(categoryToUpdate.name()));
            assertThat(category.getDescription()).isEqualTo(categoryToUpdate.description());
            assertThat(category.getTheme().getId()).isEqualTo(categoryToUpdate.themeId());
        }

        @Test
        @DisplayName("Mettre à jour une catégorie inexistante")
        public void shouldFailToUpdate_WhenNonExistingCategory() {
            // Given
            long idToUpdate = 13L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO("catégorie", "description", 1);

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.update(idToUpdate, categoryToUpdate));
        }

        @Test
        @DisplayName("Mettre à jour une catégorie avec un nom déjà existant")
        public void shouldFailToUpdate_WhenCategoryWithExistingName() {
            // Given
            long idToUpdate = 5L;
            CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO("Peinture", "", 1);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> categoryService.update(idToUpdate, categoryToUpdate));
        }
    }


    @Nested
    @DisplayName("Suppression de catégories")
    class DeletingCategories {

        @Test
        @DisplayName("Supprimer une catégorie existante")
        public void shouldDelete_WhenExistingCategory() {
            // Given
            long idToDelete = 1L;

            // When
            categoryService.delete(idToDelete);

            // Then
            assertThat(categoryRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Supprimer une catégorie inexistante")
        public void shouldFailToDelete_WhenNonExistingCategory() {
            // Given
            long idToDelete = 13L;

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.delete(idToDelete));
        }
    }


    @Nested
    @DisplayName("Désactivation / réactivation de catégories")
    class DisablingCategories {

        @Test
        @DisplayName("Désactiver une catégorie existante")
        public void shouldDisable_WhenExistingCategory() {
            // Given
            long idToDisable = 2L;
            BooleanRequestDTO booleanRequestDTO = new BooleanRequestDTO(true);

            // When
            categoryService.toggleDisable(idToDisable, booleanRequestDTO);

            // Then
            Category category = categoryRepository.findById(idToDisable).get();
            assertThat(category.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Réactiver une catégorie désactivée")
        public void shouldEnable_WhenDisabledCategory() {
            // Given
            long idToEnable = 1L;
            BooleanRequestDTO booleanRequestDTO = new BooleanRequestDTO(false);

            // When
            categoryService.toggleDisable(idToEnable, booleanRequestDTO);

            // Then
            Category category = categoryRepository.findById(idToEnable).get();
            assertThat(category.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Désactiver une catégorie inexistante")
        public void shouldFailToDisable_WhenNonExistingCategory() {
            // Given
            long idToDisable = 13L;
            BooleanRequestDTO booleanRequestDTO = new BooleanRequestDTO(true);

            // When / Then
            assertThrows(NotFoundException.class, () -> categoryService.toggleDisable(idToDisable, booleanRequestDTO));
        }
    }
}
