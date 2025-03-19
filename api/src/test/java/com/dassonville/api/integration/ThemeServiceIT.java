package com.dassonville.api.integration;

import com.dassonville.api.dto.ThemeDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.util.StringUtils.capitalize;


@SpringBootTest
@DisplayName("Tests d'intégration de la classe ThemeService")
public class ThemeServiceIT {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
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
            List<ThemeDTO> themes = themeService.getAllThemes();

            // Then
            assertThat(themes.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void shouldGetAllActiveThemes() {
            // When
            List<ThemeDTO> themes = themeService.getAllActiveThemes();

            // Then
            assertThat(themes.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void shouldGetTheme_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            ThemeDTO theme = themeService.findById(idToFind);

            // Then
            assertThat(theme.name()).isEqualTo("Art");
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant")
        public void shouldFailToGetTheme_WhenNonExistingId() {
            // Given
            long idToFind = 9L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.findById(idToFind));
        }

    }


    @Nested
    @DisplayName("Créer un thème")
    class CreatingTheme {

        @Test
        @DisplayName("Créer un thème inexistant")
        public void shouldCreate_WhenNonExistingTheme() {
            // Given
            ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO("theme", "description");

            // When
            ThemeDTO createdTheme = themeService.create(themeToCreate);

            // Then
            assertThat(createdTheme.name()).isEqualTo(capitalize(themeToCreate.name()));
            assertThat(createdTheme.createdAt()).isNotNull();
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
    @DisplayName("Mettre à jour un thème")
    class UpdatingTheme {

        @Test
        @DisplayName("Mettre à jour un thème existant")
        public void shouldUpdate_WhenExistingTheme() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("nouveau nom", "");

            // When
            ThemeDTO updatedTheme = themeService.update(1L, themeToUpdate);

            // Then
            assertThat(updatedTheme.name()).isEqualTo(capitalize(themeToUpdate.name()));
            assertThat(updatedTheme.updatedAt()).isNotNull();
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
    @DisplayName("Supprimer un thème")
    class DeletingTheme {

        @Test
        @DisplayName("Supprimer un thème existant")
        public void shouldDelete_WhenExistingTheme() {
            // Given
            long idToDelete = 1L;

            // When
            themeService.delete(idToDelete);

            // Then
            Optional<Theme> theme = themeRepository.findById(idToDelete);
            assertThat(theme).isEmpty();
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
    @DisplayName("Désactiver / réactiver un thème")
    class DisablingTheme {

        @Test
        @DisplayName("Désactiver un thème actif")
        public void shouldDisable_WhenActiveTheme() {
            // Given
            long idToDisable = 4L;

            // When
            themeService.toggleDisable(idToDisable);

            // Then
            Theme theme = themeRepository.findById(idToDisable).get();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Réactiver un thème désactivé")
        public void shouldEnable_WhenDisabledTheme() {
            // Given
            long idToEnable = 2L;

            // When
            themeService.toggleDisable(idToEnable);

            // Then
            Theme theme = themeRepository.findById(idToEnable).get();
            assertThat(theme.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Désactiver un thème inexistant")
        public void shouldFailToDisable_WhenNonExistingTheme() {
            // Given
            long idToDisable = 9L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.toggleDisable(idToDisable));
        }

    }

}
