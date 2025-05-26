package com.dassonville.api.service;


import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.ThemeMapper;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.projection.PublicThemeProjection;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Thème")
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    private ThemeService themeService;

    private final ThemeMapper themeMapper = Mappers.getMapper(ThemeMapper.class);


    private long id;
    private Theme theme;
    private PublicThemeProjection publicThemeProjection;
    private ThemeUpsertDTO themeToUpsertDTO;



    @BeforeEach
    void setUp() {
        themeService = new ThemeService(themeRepository, themeMapper);

        id = 1L;

        theme = new Theme();
        theme.setId(1L);
        theme.setName("Informatique");
        theme.setDescription("Description");
        theme.setCreatedAt(LocalDateTime.now());
        theme.setUpdatedAt(LocalDateTime.now());
        theme.setDisabledAt(null);

        publicThemeProjection = mock(PublicThemeProjection.class);
        lenient().when(publicThemeProjection.getId()).thenReturn(1L);
        lenient().when(publicThemeProjection.getName()).thenReturn("Informatique");
        lenient().when(publicThemeProjection.getDescription()).thenReturn("Description");
        lenient().when(publicThemeProjection.getCreatedAt()).thenReturn(LocalDateTime.now());

        themeToUpsertDTO = new ThemeUpsertDTO(" informatique ", " description");
    }


    @Nested
    @DisplayName("Récupérer une liste de thèmes")
    class getAllThemesTest {

        @Test
        @DisplayName("ADMIN - Récupérer toutes les catégories d'un thème (ID et nom)")
        public void getCategoriesByTheme() {
            // Given
            IdAndNameProjection idAndNameProjection = mock(IdAndNameProjection.class);

            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(themeRepository.findAllCategoriesByThemeId(anyLong()))
                    .thenReturn(List.of(idAndNameProjection));

            // When
            themeService.getCategoriesByTheme(id);

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(themeRepository).findAllCategoriesByThemeId(anyLong());
        }

        @Test
        @DisplayName("ADMIN - Erreur - Récupérer toutes les catégories d'un thème (ID et nom) - Thème non trouvé")
        public void getCategoriesByTheme_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.getCategoriesByTheme(id));

            verify(themeRepository).existsById(anyLong());
            verify(themeRepository, never()).findAllCategoriesByThemeId(anyLong());
        }


        @Test
        @DisplayName("ADMIN - Récupérer tous les thèmes en détails")
        public void getAllThemesDetails() {
            // Given
            when(themeRepository.findAllByOrderByNameAndCategoryName())
                    .thenReturn(List.of(theme));

            // When
            List<ThemeAdminDTO> results = themeService.getAllThemesDetails();

            // Then
            verify(themeRepository).findAllByOrderByNameAndCategoryName();

            assertThat(results).isNotNull();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.getFirst().id()).isEqualTo(theme.getId());
            assertThat(results.getFirst().name()).isEqualTo(theme.getName());
            assertThat(results.getFirst().description()).isEqualTo(theme.getDescription());
            assertThat(results.getFirst().createdAt()).isEqualTo(theme.getCreatedAt());
            assertThat(results.getFirst().updatedAt()).isEqualTo(theme.getUpdatedAt());
            assertThat(results.getFirst().disabledAt()).isEqualTo(theme.getDisabledAt());
        }

        @Test
        @DisplayName("USER - Récupérer tous les thèmes actifs")
        public void getAllActiveThemes() {
            // Given
            when(themeRepository.findByDisabledAtIsNullOrderByName())
                    .thenReturn(List.of(publicThemeProjection));

            // When
            List<ThemePublicDTO> results = themeService.getAllActiveThemes();

            // Then
            verify(themeRepository).findByDisabledAtIsNullOrderByName();

            assertThat(results).isNotNull();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.getFirst().id()).isEqualTo(publicThemeProjection.getId());
            assertThat(results.getFirst().name()).isEqualTo(publicThemeProjection.getName());
            assertThat(results.getFirst().description()).isEqualTo(publicThemeProjection.getDescription());
            assertThat(results.getFirst().isNew()).isEqualTo(true);
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

            // Then
            verify(themeRepository).findById(anyLong());

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(theme.getName());
        }

        @Test
        @DisplayName("ADMIN - Erreur - Thème non trouvé")
        public void findByIdForAdmin_nonExistingId() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.findByIdForAdmin(id));

            verify(themeRepository).findById(anyLong());
        }

    }


    @Nested
    @DisplayName("Créer un thème")
    class CreateTests {

        @Test
        @DisplayName("Succès")
        public void create_newTheme() {
            // Given
            when(themeRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            ThemeAdminDTO result = themeService.create(themeToUpsertDTO);

            // Then
            verify(themeRepository).existsByNameIgnoreCase(anyString());
            verify(themeRepository).save(any(Theme.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(theme.getName());
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void create_existingTheme() {
            // Given
            when(themeRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.create(themeToUpsertDTO));

            verify(themeRepository).existsByNameIgnoreCase(anyString());
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }


    @Nested
    @DisplayName("Mettre à jour un thème")
    class UpdateTests {

        @Test
        @DisplayName("Succès")
        public void update_existingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            ThemeAdminDTO result = themeService.update(id, themeToUpsertDTO);

            // Then
            verify(themeRepository).findById(anyLong());
            verify(themeRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(themeRepository).save(any(Theme.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(theme.getName());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void update_nonExistingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.update(id, themeToUpsertDTO));

            verify(themeRepository).findById(anyLong());
            verify(themeRepository, never()).existsByNameIgnoreCase(anyString());
            verify(themeRepository, never()).save(any(Theme.class));
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void update_existingThemeWithExistingName() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.update(id, themeToUpsertDTO));

            verify(themeRepository).findById(anyLong());
            verify(themeRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(themeRepository, never()).save(any(Theme.class));
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

            // When
            themeService.delete(id);

            // Then
            verify(themeRepository).existsById(anyLong());
            verify(themeRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void delete_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.delete(id));

            verify(themeRepository).existsById(anyLong());
            verify(themeRepository, never()).deleteById(anyLong());
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
            verify(themeRepository).findById(anyLong());
            verify(themeRepository, never()).countByIdAndQuizzesDisabledAtIsNull(anyLong());
            verify(themeRepository).save(any(Theme.class));
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
            verify(themeRepository).findById(anyLong());
            verify(themeRepository, never()).countByIdAndQuizzesDisabledAtIsNull(anyLong());
            verify(themeRepository, never()).save(any(Theme.class));
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

            // When & Then
            assertThrows(ActionNotAllowedException.class, () -> themeService.updateVisibility(id, true));

            verify(themeRepository).findById(anyLong());
            verify(themeRepository).countByIdAndQuizzesDisabledAtIsNull(anyLong());
            verify(themeRepository, never()).save(any(Theme.class));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void disable_nonExistingTheme() {
            // Given
            when(themeRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.updateVisibility(id, true));

            verify(themeRepository).findById(anyLong());
            verify(themeRepository, never()).countByIdAndQuizzesDisabledAtIsNull(anyLong());
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }

}
