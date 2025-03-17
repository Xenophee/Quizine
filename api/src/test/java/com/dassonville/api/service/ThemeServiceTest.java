package com.dassonville.api.service;


import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.MismatchedIdException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires de la classe ThemeService")
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeService themeService;

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme();
        theme.setId(1);
        theme.setName("informatique");
        theme.setDescription("");
    }

    private Theme createUpdatedTheme() {
        Theme updatedTheme = new Theme();
        updatedTheme.setId(1);  // Même ID que l'existant pour simuler un update
        updatedTheme.setName("nouveau nom différent");
        updatedTheme.setDescription("Nouvelle description");
        return updatedTheme;
    }


    @Nested
    @DisplayName("Tests pour getAllThemes")
    class getAllThemesTest {
        @Test
        @DisplayName("Récupérer tous les thèmes")
        public void getAllThemes() {
            // Given
            when(themeRepository.findAll())
                    .thenReturn(List.of(theme));

            // When
            themeService.getAllThemes();

            // Then
            verify(themeRepository).findAll();
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void getAllActiveThemes() {
            // Given
            when(themeRepository.findByPublishedAtIsNotNullAndDisabledAtIsNull())
                    .thenReturn(List.of(theme));

            // When
            themeService.getAllActiveThemes();

            // Then
            verify(themeRepository).findByPublishedAtIsNotNullAndDisabledAtIsNull();
        }
    }


    @Nested
    @DisplayName("Tests pour findById")
    class FindByIdTests {

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void findById_existingId() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));

            // When
            Theme result = themeService.findById(theme.getId());

            // Then
            verify(themeRepository).findById(any(Long.class));
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(theme);
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant")
        public void findById_nonExistingId() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.findById(theme.getId()));

            verify(themeRepository).findById(any(Long.class));
        }

    }


    @Nested
    @DisplayName("Tests pour create")
    class CreateTests {

        @Test
        @DisplayName("Créer un nouveau thème")
        public void create_newTheme() {
            // Given
            when(themeRepository.existsByName(any(String.class)))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            Theme result = themeService.create(theme);

            // Then
            verify(themeRepository).existsByName(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(theme);
        }

        @Test
        @DisplayName("Créer un thème déjà existant")
        public void create_existingTheme() {
            // Given
            when(themeRepository.existsByName(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.create(theme));

            verify(themeRepository).existsByName(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }


    @Nested
    @DisplayName("Tests pour update")
    class UpdateTests {

        @Test
        @DisplayName("Mettre à jour un thème existant")
        public void update_existingTheme() {

            Theme themeToUpdate = createUpdatedTheme();

            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByName(any(String.class)))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(themeToUpdate);

            // When
            Theme result = themeService.update(theme.getId(), themeToUpdate);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).existsByName(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(themeToUpdate);
        }

        @Test
        @DisplayName("Mettre à jour un thème sans changer le nom")
        public void update_existingThemeWithSameName() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            Theme result = themeService.update(theme.getId(), theme);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository, never()).existsByName(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(theme);
        }


        @Test
        @DisplayName("Mettre à jour un thème avec ID discordant")
        public void update_mismatchedId() {
            // Given
            Theme themeToUpdate = new Theme();
            themeToUpdate.setId(2);

            // When & Then
            assertThrows(MismatchedIdException.class, () -> themeService.update(theme.getId(), themeToUpdate));

            verify(themeRepository, never()).findById(any(Long.class));
            verify(themeRepository, never()).existsByName(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

        @Test
        @DisplayName("Mettre à jour un thème inexistant")
        public void update_nonExistingTheme() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.update(theme.getId(), theme));

            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository, never()).existsByName(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

        @Test
        @DisplayName("Mettre à jour un thème avec un nom déjà existant")
        public void update_existingThemeWithExistingName() {
            // Given
            Theme themeToUpdate = createUpdatedTheme();

            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByName(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.update(theme.getId(), themeToUpdate));

            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).existsByName(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }


    @Nested
    @DisplayName("Tests pour delete")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer un thème existant")
        public void delete_existingTheme() {
            // Given
            when(themeRepository.existsById(any(Long.class)))
                    .thenReturn(true);

            // When
            themeService.delete(theme.getId());

            // Then
            verify(themeRepository).existsById(any(Long.class));
            verify(themeRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("Supprimer un thème inexistant")
        public void delete_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(any(Long.class)))
                    .thenReturn(false);

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.delete(theme.getId()));

            verify(themeRepository).existsById(any(Long.class));
            verify(themeRepository, never()).deleteById(any(Long.class));
        }

    }

}
