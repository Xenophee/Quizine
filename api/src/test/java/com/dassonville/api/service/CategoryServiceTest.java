package com.dassonville.api.service;


import com.dassonville.api.dto.request.CategoryUpsertDTO;
import com.dassonville.api.dto.response.CategoryAdminDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.CategoryMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TestIdAndNameProjection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("service")
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

        category = Category.builder()
                .id(id)
                .name("Catégorie")
                .description("Description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .theme(new Theme(1L))
                .build();

        categoryUpsertDTO = new CategoryUpsertDTO(" catégorie", " description");
    }


    @Nested
    @DisplayName("Récupérer les catégories par thème")
    class GetCategoriesByThemeTest {

        @Test
        @DisplayName("Succès")
        void getCategoriesByTheme_existingTheme() {
            // Given
            IdAndNameProjection projection = new TestIdAndNameProjection();

            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(categoryRepository.findAllByThemeIdOrderByName(anyLong()))
                    .thenReturn(List.of(projection));

            // When
            List<IdAndNameProjection> result = categoryService.findAllByTheme(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(categoryRepository).findAllByThemeIdOrderByName(id)
            );

            assertAll("Assertions for projection",
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result).hasSize(1)
            );
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void getCategoriesByTheme_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.findAllByTheme(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).existsById(id),
                    () -> verifyNoInteractions(categoryRepository)
            );
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
            assertAll("Verify methods calls",
                    () -> verify(categoryRepository).findById(id)
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.id()).isEqualTo(category.getId()),
                    () -> assertThat(result.name()).isEqualTo(category.getName()),
                    () -> assertThat(result.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(category.getTheme().getId())
            );
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void findById_nonExistingId() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.findById(id));

            // Then
            verify(categoryRepository).findById(anyLong());

            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_NOT_FOUND),
                    () -> verify(categoryRepository).findById(id)
            );
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
            CategoryAdminDTO result = categoryService.create(id, categoryUpsertDTO);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(categoryRepository).existsByNameIgnoreCase(anyString()),
                    () -> verify(categoryRepository).save(any(Category.class))
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.name()).isEqualTo(category.getName()),
                    () -> assertThat(result.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(category.getTheme().getId())
            );
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void create_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.create(id, categoryUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).existsById(id),
                    () -> verifyNoInteractions(categoryRepository)
            );
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
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> categoryService.create(id, categoryUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_ALREADY_EXISTS),
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(categoryRepository).existsByNameIgnoreCase(category.getName()),
                    () -> verifyNoMoreInteractions(categoryRepository)
            );
        }
    }


    @Nested
    @DisplayName("Mise à jour d'une catégorie")
    class UpdateTest {

        private Category categoryUpdated;
        private CategoryUpsertDTO categoryToUpdateDTO;

        @BeforeEach
        void setUpUpdate() {
            categoryUpdated = Category.builder()
                    .id(id)
                    .name("Catégorie mise à jour")
                    .description("Description mise à jour")
                    .createdAt(category.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(category.getDisabledAt())
                    .theme(category.getTheme())
                    .build();

            categoryToUpdateDTO = new CategoryUpsertDTO(" catégorie mise à jour  ", " description mise à jour  ");
        }

        @Test
        @DisplayName("Succès")
        void update_existingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(category));
            when(categoryRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);

            // When
            CategoryAdminDTO result = categoryService.update(id, categoryToUpdateDTO);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(categoryRepository).findById(id),
                    () -> verify(categoryRepository).existsByNameIgnoreCaseAndIdNot(anyString(), eq(id))
            );

            assertAll("Verify updated entity",
                    () -> assertThat(category.getName()).isEqualTo(categoryUpdated.getName()),
                    () -> assertThat(category.getDescription()).isEqualTo(categoryUpdated.getDescription())
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.id()).isEqualTo(category.getId()),
                    () -> assertThat(result.name()).isEqualTo(category.getName()),
                    () -> assertThat(result.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(category.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(category.getTheme().getId())
            );
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void update_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.update(id, categoryToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_NOT_FOUND),
                    () -> verify(categoryRepository).findById(id),
                    () -> verifyNoMoreInteractions(categoryRepository)
            );
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
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> categoryService.update(id, categoryToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_ALREADY_EXISTS),
                    () -> verify(categoryRepository).findById(id),
                    () -> verify(categoryRepository).existsByNameIgnoreCaseAndIdNot(anyString(), eq(id))
            );
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
            assertAll("Verify methods calls",
                    () -> verify(categoryRepository).existsById(id),
                    () -> verify(categoryRepository).deleteById(id)
            );
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void delete_nonExistingCategory() {
            // Given
            when(categoryRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_NOT_FOUND),
                    () -> verify(categoryRepository).existsById(id),
                    () -> verifyNoMoreInteractions(categoryRepository)
            );
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
            assertAll(
                    () -> verify(categoryRepository).findById(id),
                    () -> assertThat(category.isVisible()).isFalse()
            );
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
            assertAll(
                    () -> verify(categoryRepository).findById(id),
                    () -> assertThat(category.isVisible()).isTrue()
            );
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        void disable_nonExistingCategory() {
            // Given
            when(categoryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.updateVisibility(id, false));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CATEGORY_NOT_FOUND),
                    () -> verify(categoryRepository).findById(id)
            );
        }
    }
}
