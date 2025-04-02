package com.dassonville.api.service;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - CategoryService")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;


    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    private long id;
    private ToggleDisableRequestDTO toggleDisableRequestDTO;
    private Category category;
    private Category categoryToUpdate;
    private CategoryAdminDTO categoryAdminDTO;
    private CategoryUpsertDTO categoryToCreateDTO;
    private CategoryUpsertDTO categoryToUpdateDTO;


    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, categoryMapper);

        id = 1L;
        toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);
        Theme theme = new Theme();
        theme.setId(1);

        category = new Category();
        category.setId(1);
        category.setName("informatique");
        category.setDescription("");
        category.setTheme(theme);

        categoryToUpdate = new Category();
        categoryToUpdate.setId(1);
        categoryToUpdate.setName("Nouveau nom");
        categoryToUpdate.setDescription("Nouvelle description");
        categoryToUpdate.setTheme(theme);

        categoryAdminDTO = categoryMapper.toAdminDTO(category);
        categoryToCreateDTO = categoryMapper.toUpsertDTO(category);
        categoryToUpdateDTO = categoryMapper.toUpsertDTO(categoryToUpdate);
    }


    @Nested
    @DisplayName("Tests de la méthode findById")
    class FindByIdTest {

        @Test
        @DisplayName("Récupérer une catégorie par son ID")
        void findById_existingId() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(category));

            // When
            CategoryAdminDTO result = categoryService.findById(id);

            // Then
            verify(categoryRepository).findById(any(Long.class));
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(categoryAdminDTO);
        }

        @Test
        @DisplayName("Récupérer une catégorie par un ID inexistant")
        void findById_nonExistingId() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.findById(id));

            // Then
            verify(categoryRepository).findById(any(Long.class));
        }
    }


    @Nested
    @DisplayName("Tests de la méthode create")
    class CreateTest {

        @Test
        @DisplayName("Créer une nouvelle catégorie")
        void create_newCategory() {
            // Given
            when(categoryRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(category);

            // When
            CategoryAdminDTO result = categoryService.create(categoryToCreateDTO);

            // Then
            verify(categoryRepository).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository).save(any(Category.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(categoryToCreateDTO.name());
        }

        @Test
        @DisplayName("Créer une catégorie avec un nom déjà existant")
        void create_existingCategory() {
            // Given
            when(categoryRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> categoryService.create(categoryToCreateDTO));

            // Then
            verify(categoryRepository).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }


    @Nested
    @DisplayName("Tests de la méthode update")
    class UpdateTest {

        @Test
        @DisplayName("Mettre à jour une catégorie existante")
        void update_existingCategory() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(categoryToUpdate);

            // When
            CategoryAdminDTO result = categoryService.update(id, categoryToUpdateDTO);

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository).save(any(Category.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(categoryToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour une catégorie sans changer le nom")
        void update_existingCategoryWithoutChangingName() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(categoryToUpdate));
            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(categoryToUpdate);

            // When
            CategoryAdminDTO result = categoryService.update(id, categoryToUpdateDTO);

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository).save(any(Category.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(categoryToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour une catégorie inexistante")
        void update_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.update(id, categoryToUpdateDTO));

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository, never()).save(any(Category.class));
        }

        @Test
        @DisplayName("Mettre à jour une catégorie avec un nom déjà existant")
        void update_existingCategoryWithExistingName() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> categoryService.update(id, categoryToUpdateDTO));

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository).existsByNameIgnoreCase(any(String.class));
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }


    @Nested
    @DisplayName("Tests de la méthode delete")
    class DeleteTest {

        @Test
        @DisplayName("Supprimer une catégorie existante")
        void delete_existingCategory() {
            // Given
            when(categoryRepository.existsById(any(Long.class)))
                    .thenReturn(true);

            // When
            categoryService.delete(id);

            // Then
            verify(categoryRepository).existsById(any(Long.class));
            verify(categoryRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("Supprimer une catégorie inexistante")
        void delete_nonExistingCategory() {
            // Given
            when(categoryRepository.existsById(any(Long.class)))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> categoryService.delete(id));

            // Then
            verify(categoryRepository).existsById(any(Long.class));
            verify(categoryRepository, never()).deleteById(any(Long.class));
        }
    }


    @Nested
    @DisplayName("Tests de la méthode toggleDisable")
    class ToggleDisableTest {

        @Test
        @DisplayName("Désactiver une catégorie existante")
        void disable_activeCategory() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(category));

            // When
            categoryService.toggleDisable(id, toggleDisableRequestDTO);

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository).save(any(Category.class));
        }

        @Test
        @DisplayName("Réactiver une catégorie désactivée")
        void enable_disabledCategory() {
            // Given
            toggleDisableRequestDTO = new ToggleDisableRequestDTO(false);
            category.setDisabledAt(category.getCreatedAt());
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(category));

            // When
            categoryService.toggleDisable(id, toggleDisableRequestDTO);

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository).save(any(Category.class));
        }

        @Test
        @DisplayName("Désactiver une catégorie inexistante")
        void disable_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.toggleDisable(id, toggleDisableRequestDTO));

            // Then
            verify(categoryRepository).findById(any(Long.class));
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }
}
