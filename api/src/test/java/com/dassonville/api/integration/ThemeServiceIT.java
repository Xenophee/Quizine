package com.dassonville.api.integration;

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
            List<Theme> themes = themeService.getAllThemes();

            // Then
            assertThat(themes.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void shouldGetAllActiveThemes() {
            // When
            List<Theme> themes = themeService.getAllActiveThemes();

            // Then
            assertThat(themes.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void shouldGetTheme_WhenExistingId() {
            // Given
            Long idToFind = 1L;

            // When
            Theme theme = themeService.findById(idToFind);

            // Then
            assertThat(theme.getName()).isEqualTo("Art");
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant")
        public void shouldFailToGetTheme_WhenNonExistingId() {
            // Given
            Long idToFind = 9L;

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
            Theme themeToCreate = new Theme();
            themeToCreate.setName("theme");
            themeToCreate.setDescription("description");

            // When
            Theme createdTheme = themeService.create(themeToCreate);

            // Then
            assertThat(createdTheme.getName()).isEqualTo(capitalize(themeToCreate.getName()));
            assertThat(createdTheme.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Créer un thème déjà existant")
        public void shouldFailToCreate_WhenExistingTheme() {
            // Given
            Theme themeToCreate = new Theme();
            themeToCreate.setName("Art");
            themeToCreate.setDescription("");

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
            Theme themeToUpdate = new Theme();
            themeToUpdate.setId(1L);
            themeToUpdate.setName("nouveau nom différent");
            themeToUpdate.setDescription("");

            // When
            Theme updatedTheme = themeService.update(themeToUpdate.getId(), themeToUpdate);

            // Then
            assertThat(updatedTheme.getName()).isEqualTo(capitalize(themeToUpdate.getName()));
            assertThat(updatedTheme.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Mettre à jour un thème inexistant")
        public void shouldFailToUpdate_WhenNonExistingTheme() {
            // Given
            Theme themeToUpdate = new Theme();
            themeToUpdate.setId(9L);
            themeToUpdate.setName("nouveau nom");
            themeToUpdate.setDescription("");

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.update(themeToUpdate.getId(), themeToUpdate));
        }

        @Test
        @DisplayName("Mettre à jour un thème avec un nom déjà existant")
        public void shouldFailToUpdate_WhenThemeWithExistingName() {
            // Given
            Theme themeToUpdate = new Theme();
            themeToUpdate.setId(2L);
            themeToUpdate.setName("Art");
            themeToUpdate.setDescription("");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> themeService.update(themeToUpdate.getId(), themeToUpdate));
        }

    }


    @Nested
    @DisplayName("Supprimer un thème")
    class DeletingTheme {

        @Test
        @DisplayName("Supprimer un thème existant")
        public void shouldDelete_WhenExistingTheme() {
            // Given
            Long idToDelete = 1L;

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
            Long idToDelete = 9L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.delete(idToDelete));
        }

    }

}
