package com.dassonville.api.integration;

import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.service.ThemeService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.util.StringUtils.capitalize;


@SpringBootTest
@DisplayName("IT - ThemeService")
public class ThemeServiceIT {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }



    @Nested
    @DisplayName("Récupération de thèmes")
    class GettingThemes {

        @Test
        @DisplayName("Récupérer tous les thèmes")
        public void shouldGetAllThemes() {
            // When
            List<ThemeAdminDTO> themes = themeService.getAllThemes();

            // Then
            assertThat(themes.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void shouldGetAllActiveThemes() {
            // When
            List<ThemePublicDTO> themes = themeService.getAllActiveThemes();

            // Then
            assertThat(themes.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void shouldGetTheme_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            ThemeAdminDTO theme = themeService.findByIdForAdmin(idToFind);

            // Then
            assertThat(theme.name()).isEqualTo("Art");
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant")
        public void shouldFailToGetTheme_WhenNonExistingId() {
            // Given
            long idToFind = 9L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.findByIdForAdmin(idToFind));
        }

    }


    @Nested
    @DisplayName("Création de thèmes")
    class CreatingTheme {

        @Test
        @DisplayName("Créer un thème inexistant")
        public void shouldCreate_WhenNonExistingTheme() {
            // Given
            ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO("theme", "description");

            // When
            ThemeAdminDTO createdTheme = themeService.create(themeToCreate);

            // Then
            Theme theme = themeRepository.findById(createdTheme.id()).get();
            assertThat(theme.getName()).isEqualTo(capitalize(themeToCreate.name()));
            assertThat(theme.getCreatedAt()).isNotNull();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Créer un thème déjà existant")
        public void shouldFailToCreate_WhenExistingTheme() {
            // Given
            ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO("Art", "");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> themeService.create(themeToCreate));
        }

    }


    @Nested
    @DisplayName("Mise à jour de thèmes")
    class UpdatingTheme {

        @Test
        @DisplayName("Mettre à jour un thème existant")
        public void shouldUpdate_WhenExistingTheme() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("nouveau nom", "");

            // When
            ThemeAdminDTO updatedTheme = themeService.update(1L, themeToUpdate);

            // Then
            Theme theme = themeRepository.findById(updatedTheme.id()).get();
            assertThat(theme.getName()).isEqualTo(capitalize(themeToUpdate.name()));
            assertThat(theme.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Mettre à jour un thème inexistant")
        public void shouldFailToUpdate_WhenNonExistingTheme() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("nouveau nom", "");

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.update(9L, themeToUpdate));
        }

        @Test
        @DisplayName("Mettre à jour un thème avec un nom déjà existant")
        public void shouldFailToUpdate_WhenThemeWithExistingName() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("Art", "");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> themeService.update(2L, themeToUpdate));
        }

    }


    @Nested
    @DisplayName("Suppression de thèmes")
    class DeletingTheme {

        @Test
        @DisplayName("Supprimer un thème existant")
        public void shouldDelete_WhenExistingTheme() {
            // Given
            long idToDelete = 1L;

            // When
            themeService.delete(idToDelete);

            // Then
            assertThat(themeRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Supprimer un thème inexistant")
        public void shouldFailToDelete_WhenNonExistingTheme() {
            // Given
            long idToDelete = 9L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.delete(idToDelete));
        }

    }


    @Nested
    @DisplayName("Désactivation / réactivation de thèmes")
    class DisablingTheme {

        @Test
        @DisplayName("Désactiver un thème actif")
        public void shouldDisable_WhenActiveTheme() {
            // Given
            long idToDisable = 4L;
            ToggleDisableRequestDTO toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);

            // When
            themeService.toggleDisable(idToDisable, toggleDisableRequestDTO);

            // Then
            Theme theme = themeRepository.findById(idToDisable).get();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Réactiver un thème désactivé")
        public void shouldEnable_WhenDisabledTheme() {
            // Given
            long idToEnable = 2L;
            ToggleDisableRequestDTO toggleDisableRequestDTO = new ToggleDisableRequestDTO(false);

            // When
            themeService.toggleDisable(idToEnable, toggleDisableRequestDTO);

            // Then
            Theme theme = themeRepository.findById(idToEnable).get();
            assertThat(theme.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Désactiver un thème inexistant")
        public void shouldFailToDisable_WhenNonExistingTheme() {
            // Given
            long idToDisable = 9L;
            ToggleDisableRequestDTO toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.toggleDisable(idToDisable, toggleDisableRequestDTO));
        }

    }

}
