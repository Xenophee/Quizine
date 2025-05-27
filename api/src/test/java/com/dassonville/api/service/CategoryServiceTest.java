package com.dassonville.api.service;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Catégorie")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ThemeRepository themeRepository;

    private CategoryService categoryService;


    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);


    private long id;
    private Category category;
    private CategoryUpsertDTO categoryUpsertDTO;


    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, categoryMapper, themeRepository);

        id = 1L;

        category = new Category();
        category.setId(1L);
        category.setName("Catégorie");
        category.setDescription("Description");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setDisabledAt(null);
        category.setTheme(new Theme(1L));

        categoryUpsertDTO = new CategoryUpsertDTO(" catégorie", " description");
    }


    @Nested
    @DisplayName("Récupérer les catégories par thème")
    class GetCategoriesByThemeTest {

        @Test
        @DisplayName("Succès")
        void getCategoriesByTheme_existingTheme() {
            // Given
            IdAndNameProjection idAndNameProjection = mock(IdAndNameProjection.class);
            when(idAndNameProjection.getId()).thenReturn(1L);
            when(idAndNameProjection.getName()).thenReturn("Catégorie");

            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(categoryRepository.findAllByThemeIdOrderByName(anyLong()))
                    .thenReturn(List.of(idAndNameProjection));

            // When
            List<IdAndNameProjection> result = categoryService.findAllByTheme(1L);

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(categoryRepository).findAllByThemeIdOrderByName(anyLong());

            assertThat(result).isNotNull();
            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getId()).isEqualTo(category.getId());
            assertThat(result.getFirst().getName()).isEqualTo(category.getName());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void getCategoriesByTheme_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> categoryService.findAllByTheme(1L));

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(categoryRepository, never()).findAllByThemeIdOrderByName(anyLong());
        }
    }


    @Nested
    @DisplayName("Récupérer une catégorie par son ID")
    class FindByIdTest {

        @Test
        @DisplayName("Succès")
        void findById_existingId() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));

            // When
            CategoryAdminDTO result = categoryService.findById(id);

            // Then
            verify(categoryRepository).findById(anyLong());

            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(category.getId());
            assertThat(result.name()).isEqualTo(category.getName());
            assertThat(result.description()).isEqualTo(category.getDescription());
            assertThat(result.createdAt()).isEqualTo(category.getCreatedAt());
            assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt());
            assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt());
            assertThat(result.themeId()).isEqualTo(category.getTheme().getId());
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void findById_nonExistingId() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.findById(id));

            // Then
            verify(categoryRepository).findById(anyLong());
        }
    }


    @Nested
    @DisplayName("Créer une catégorie")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newCategory() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(categoryRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(false);
            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(category);

            // When
            CategoryAdminDTO result = categoryService.create(1L, categoryUpsertDTO);

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(categoryRepository).existsByNameIgnoreCase(anyString());
            verify(categoryRepository).save(any(Category.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(category.getName());
            assertThat(result.description()).isEqualTo(category.getDescription());
            assertThat(result.createdAt()).isEqualTo(category.getCreatedAt());
            assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt());
            assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt());
            assertThat(result.themeId()).isEqualTo(category.getTheme().getId());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void create_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> categoryService.create(1L, categoryUpsertDTO));

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(categoryRepository, never()).existsByNameIgnoreCase(anyString());
            verify(categoryRepository, never()).save(any(Category.class));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        void create_existingCategory() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(categoryRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> categoryService.create(1L, categoryUpsertDTO));

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(categoryRepository).existsByNameIgnoreCase(anyString());
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }


    @Nested
    @DisplayName("Mise à jour d'une catégorie")
    class UpdateTest {

        @Test
        @DisplayName("Succès")
        void update_existingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(category);

            // When
            CategoryAdminDTO result = categoryService.update(id, categoryUpsertDTO);

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(categoryRepository).save(any(Category.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(category.getName());
            assertThat(result.description()).isEqualTo(category.getDescription());
            assertThat(result.createdAt()).isEqualTo(category.getCreatedAt());
            assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt());
            assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt());
            assertThat(result.themeId()).isEqualTo(category.getTheme().getId());
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void update_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.update(id, categoryUpsertDTO));

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository, never()).existsByNameIgnoreCase(anyString());
            verify(categoryRepository, never()).save(any(Category.class));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        void update_existingCategoryWithExistingName() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> categoryService.update(id, categoryUpsertDTO));

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }


    @Nested
    @DisplayName("Supprimer une catégorie")
    class DeleteTest {

        @Test
        @DisplayName("Succès")
        void delete_existingCategory() {
            // Given
            when(categoryRepository.existsById(anyLong()))
                    .thenReturn(true);

            // When
            categoryService.delete(id);

            // Then
            verify(categoryRepository).existsById(anyLong());
            verify(categoryRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void delete_nonExistingCategory() {
            // Given
            when(categoryRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> categoryService.delete(id));

            // Then
            verify(categoryRepository).existsById(anyLong());
            verify(categoryRepository, never()).deleteById(anyLong());
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'une catégorie")
    class UpdateDisableTest {

        @Test
        @DisplayName("Succès")
        void disable_activeCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));

            // When
            categoryService.updateVisibility(id, false);

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository).save(any(Category.class));
        }

        @Test
        @DisplayName("RAS - Pas de changement d'état")
        void disable_noChange() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));

            // When
            categoryService.updateVisibility(id, true);

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository, never()).save(any(Category.class));
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void disable_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> categoryService.updateVisibility(id, false));

            // Then
            verify(categoryRepository).findById(anyLong());
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }
}
