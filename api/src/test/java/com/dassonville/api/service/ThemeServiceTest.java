package com.dassonville.api.service;


import com.dassonville.api.dto.request.ThemeUpsertDTO;
import com.dassonville.api.dto.response.CategoryInfoThemeAdminDTO;
import com.dassonville.api.dto.response.ThemeAdminDTO;
import com.dassonville.api.dto.response.ThemePublicDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.ThemeMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TestPublicThemeProjection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
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
@DisplayName("UNI - Service : Thème")
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    private ThemeService themeService;

    private final ThemeMapper themeMapper = Mappers.getMapper(ThemeMapper.class);


    private long id;
    private long idCategory;
    private Theme theme;
    private Category category;
    private ThemeUpsertDTO themeToUpsertDTO;

    private LocalDateTime FIXED_LOCALDATETIME = LocalDateTime.now();


    @BeforeEach
    void setUp() {
        themeService = new ThemeService(themeRepository, themeMapper);

        id = 1L;
        idCategory = 2L;

        category = Category.builder()
                .id(idCategory)
                .name("Catégorie")
                .description("Description de la catégorie")
                .createdAt(FIXED_LOCALDATETIME)
                .updatedAt(FIXED_LOCALDATETIME)
                .disabledAt(null)
                .build();

        theme = Theme.builder()
                .id(id)
                .name("Nom")
                .description("Description")
                .isDefault(false)
                .createdAt(FIXED_LOCALDATETIME)
                .updatedAt(FIXED_LOCALDATETIME)
                .disabledAt(null)
                .categories(List.of(category))
                .build();

        themeToUpsertDTO = new ThemeUpsertDTO(" nom  ", " description  ");
    }


    @Nested
    @DisplayName("Récupérer une liste de thèmes")
    class getAllThemesTest {

        @Test
        @DisplayName("ADMIN - Récupérer tous les thèmes en détails")
        public void getAllThemesDetails() {
            // Given
            when(themeRepository.findAllByOrderByNameAndCategoryName())
                    .thenReturn(List.of(theme));

            // When
            List<ThemeAdminDTO> results = themeService.getAllDetails();
            ThemeAdminDTO themeDTO = results.getFirst();
            CategoryInfoThemeAdminDTO categoryDTO = themeDTO.categories().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).findAllByOrderByNameAndCategoryName()
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(results).isNotEmpty(),
                    () -> assertThat(results).hasSize(1),
                    () -> assertThat(themeDTO.id()).isEqualTo(theme.getId()),
                    () -> assertThat(themeDTO.name()).isEqualTo(theme.getName()),
                    () -> assertThat(themeDTO.description()).isEqualTo(theme.getDescription()),
                    () -> assertThat(themeDTO.isDefault()).isEqualTo(theme.getIsDefault()),
                    () -> assertThat(themeDTO.createdAt()).isEqualTo(theme.getCreatedAt()),
                    () -> assertThat(themeDTO.updatedAt()).isEqualTo(theme.getUpdatedAt()),
                    () -> assertThat(themeDTO.disabledAt()).isEqualTo(theme.getDisabledAt()),

                    () -> assertThat(themeDTO.categories()).isNotEmpty(),
                    () -> assertThat(themeDTO.categories()).hasSize(1),
                    () -> assertThat(categoryDTO.id()).isEqualTo(category.getId()),
                    () -> assertThat(categoryDTO.name()).isEqualTo(category.getName()),
                    () -> assertThat(categoryDTO.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(categoryDTO.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(categoryDTO.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(categoryDTO.disabledAt()).isEqualTo(category.getDisabledAt())
            );
        }

        @Test
        @DisplayName("USER - Récupérer tous les thèmes actifs")
        public void getAllActiveThemes() {
            // Given
            TestPublicThemeProjection publicThemeProjection = new TestPublicThemeProjection();

            when(themeRepository.findByDisabledAtIsNullOrderByNameWithDefaultLast())
                    .thenReturn(List.of(publicThemeProjection));

            // When
            List<ThemePublicDTO> results = themeService.getAllActive();
            ThemePublicDTO theme = results.getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).findByDisabledAtIsNullOrderByNameWithDefaultLast()
            );

            assertAll("Assertions for public themes",
                    () -> assertThat(results).isNotEmpty(),
                    () -> assertThat(results).hasSize(1),
                    () -> assertThat(theme.id()).isEqualTo(publicThemeProjection.getId()),
                    () -> assertThat(theme.name()).isEqualTo(publicThemeProjection.getName()),
                    () -> assertThat(theme.description()).isEqualTo(publicThemeProjection.getDescription()),
                    () -> assertThat(theme.isNew()).isTrue()
            );
        }
    }


    @Nested
    @DisplayName("Récupérer un thème par son ID")
    class FindByIdTests {

        @Test
        @DisplayName("ADMIN - Succès")
        public void findByIdForAdmin_existingId() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));

            // When
            ThemeAdminDTO result = themeService.findByIdForAdmin(id);
            CategoryInfoThemeAdminDTO categories = result.categories().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).findById(id)
            );


            assertAll("Assertions for DTO",
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.id()).isEqualTo(theme.getId()),
                    () -> assertThat(result.name()).isEqualTo(theme.getName()),
                    () -> assertThat(result.description()).isEqualTo(theme.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(theme.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(theme.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(theme.getDisabledAt()),

                    () -> assertThat(result.categories()).isNotEmpty(),
                    () -> assertThat(result.categories()).hasSize(1),
                    () -> assertThat(categories.id()).isEqualTo(category.getId()),
                    () -> assertThat(categories.name()).isEqualTo(category.getName()),
                    () -> assertThat(categories.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(categories.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(categories.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(categories.disabledAt()).isEqualTo(category.getDisabledAt())
            );
        }

        @Test
        @DisplayName("ADMIN - Erreur - Thème non trouvé")
        public void findByIdForAdmin_nonExistingId() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> themeService.findByIdForAdmin(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).findById(id)
            );
        }

    }


    @Nested
    @DisplayName("Créer un thème")
    class CreateTests {

        @Test
        @DisplayName("Succès")
        public void create_newTheme() {
            // Given
            theme.setCategories(List.of());

            when(themeRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            ThemeAdminDTO result = themeService.create(themeToUpsertDTO);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsByNameIgnoreCase(anyString()),
                    () -> verify(themeRepository).save(any(Theme.class))
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.id()).isEqualTo(theme.getId()),
                    () -> assertThat(result.name()).isEqualTo(theme.getName()),
                    () -> assertThat(result.description()).isEqualTo(theme.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(theme.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(theme.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(theme.getDisabledAt()),
                    () -> assertThat(result.categories()).isEmpty()
            );
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void create_existingTheme() {
            // Given
            when(themeRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> themeService.create(themeToUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_ALREADY_EXISTS),
                    () -> verify(themeRepository).existsByNameIgnoreCase(anyString()),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }

    }


    @Nested
    @DisplayName("Mettre à jour un thème")
    class UpdateTests {

        private Theme themeUpdated;
        private ThemeUpsertDTO themeToUpdateDTO;

        @BeforeEach
        void setUpUpdate() {
            themeUpdated = Theme.builder()
                    .id(id)
                    .name("Nouveau nom")
                    .description("Nouvelle description")
                    .isDefault(theme.getIsDefault())
                    .createdAt(theme.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(null)
                    .categories(theme.getCategories())
                    .build();

            themeToUpdateDTO = new ThemeUpsertDTO(" nouveau nom  ", " nouvelle description  ");
        }

        @Test
        @DisplayName("Succès")
        public void update_existingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);

            // When
            ThemeAdminDTO result = themeService.update(id, themeToUpdateDTO);
            CategoryInfoThemeAdminDTO resultCategory = result.categories().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).findById(id),
                    () -> verify(themeRepository).existsByNameIgnoreCaseAndIdNot(anyString(), eq(id))
            );

            assertAll("Verify updated entity",
                    () -> assertThat(theme.getName()).isEqualTo(themeUpdated.getName()),
                    () -> assertThat(theme.getDescription()).isEqualTo(themeUpdated.getDescription())
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.id()).isEqualTo(theme.getId()),
                    () -> assertThat(result.name()).isEqualTo(theme.getName()),
                    () -> assertThat(result.description()).isEqualTo(theme.getDescription()),
                    () -> assertThat(result.isDefault()).isEqualTo(theme.getIsDefault()),
                    () -> assertThat(result.createdAt()).isEqualTo(theme.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(theme.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(theme.getDisabledAt()),

                    () -> assertThat(result.categories()).hasSize(1),
                    () -> assertThat(resultCategory.id()).isEqualTo(category.getId()),
                    () -> assertThat(resultCategory.name()).isEqualTo(category.getName()),
                    () -> assertThat(resultCategory.description()).isEqualTo(category.getDescription()),
                    () -> assertThat(resultCategory.createdAt()).isEqualTo(category.getCreatedAt()),
                    () -> assertThat(resultCategory.updatedAt()).isEqualTo(category.getUpdatedAt()),
                    () -> assertThat(resultCategory.disabledAt()).isEqualTo(category.getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void update_nonExistingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> themeService.update(id, themeToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).findById(id),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void update_existingThemeWithExistingName() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> themeService.update(id, themeToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_ALREADY_EXISTS),
                    () -> verify(themeRepository).findById(id),
                    () -> verify(themeRepository).existsByNameIgnoreCaseAndIdNot(anyString(), eq(id))
            );
        }

    }


    @Nested
    @DisplayName("Supprimer un thème")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        public void delete_existingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(themeRepository.existsByIdAndIsDefaultTrue(anyLong()))
                    .thenReturn(false);
            when(themeRepository.existsByIdAndQuizzesIsNotEmpty(anyLong()))
                    .thenReturn(false);

            // When
            themeService.delete(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(themeRepository).existsByIdAndIsDefaultTrue(id),
                    () -> verify(themeRepository).existsByIdAndQuizzesIsNotEmpty(id),
                    () -> verify(themeRepository).deleteById(id)
            );
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void delete_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> themeService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).existsById(id),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Action non autorisée - Thème par défaut")
        public void delete_defaultTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(themeRepository.existsByIdAndIsDefaultTrue(anyLong()))
                    .thenReturn(true);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> themeService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_IS_DEFAULT),
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(themeRepository).existsByIdAndIsDefaultTrue(id),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Action non autorisée - Thème avec quiz")
        public void delete_themeWithQuizzes() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(themeRepository.existsByIdAndIsDefaultTrue(anyLong()))
                    .thenReturn(false);
            when(themeRepository.existsByIdAndQuizzesIsNotEmpty(anyLong()))
                    .thenReturn(true);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> themeService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_CONTAINS_QUIZZES),
                    () -> verify(themeRepository).existsById(id),
                    () -> verify(themeRepository).existsByIdAndIsDefaultTrue(id),
                    () -> verify(themeRepository).existsByIdAndQuizzesIsNotEmpty(id),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }

    }


    @Nested
    @DisplayName("Basculer la visibilité d'un thème")
    class UpdateVisibilityTests {

        @Test
        @DisplayName("Succès")
        public void disable_activeTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));

            // When
            themeService.updateVisibility(id, false);

            // Then
            assertAll(
                    () -> verify(themeRepository).findById(id),
                    () -> verifyNoMoreInteractions(themeRepository),
                    () -> assertThat(theme.isVisible()).isFalse()
            );
        }

        @Test
        @DisplayName("RAS - Thème déjà activé")
        public void disable_alreadyDisabledTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));

            // When
            themeService.updateVisibility(id, true);

            // Then
            assertAll(
                    () -> verify(themeRepository).findById(id),
                    () -> verifyNoMoreInteractions(themeRepository),
                    () -> assertThat(theme.isVisible()).isTrue()
            );
        }


        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void disable_nonExistingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> themeService.updateVisibility(id, true));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).findById(id),
                    () -> verifyNoMoreInteractions(themeRepository)
            );
        }
        
        @Test
        @DisplayName("Erreur - Action non autorisée - Thème sans quiz actif")
        public void enable_disabledThemeWithoutActiveQuizzes() {
            // Given
            theme.setDisabledAt(LocalDateTime.now());

            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.countByIdAndQuizzesDisabledAtIsNull(anyLong()))
                    .thenReturn(0);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> themeService.updateVisibility(id, true));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_CONTAINS_NO_QUIZZES),
                    () -> verify(themeRepository).findById(id),
                    () -> verify(themeRepository).countByIdAndQuizzesDisabledAtIsNull(id)
            );
        }
    }

}
