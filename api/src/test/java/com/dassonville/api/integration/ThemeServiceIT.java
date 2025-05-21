package com.dassonville.api.integration;

import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
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


@SpringBootTest
@DisplayName("IT - Service : Thème")
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
        @DisplayName("Succès - Récupérer tous les thèmes")
        public void shouldGetAllThemes() {
            // When
            List<ThemeAdminDTO> themes = themeService.getAllThemesDetails();

            // Then
            assertThat(themes.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Succès - Récupérer tous les thèmes actifs")
        public void shouldGetAllActiveThemes() {
            // When
            List<ThemePublicDTO> themes = themeService.getAllActiveThemes();

            // Then
            assertThat(themes.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("Succès - Récupérer tous les thèmes (ID et nom) par ordre alphabétique")
        public void shouldGetAllThemesIdAndName() {
            // When
            List<IdAndNameProjection> themes = themeService.getAllThemes();

            // Then
            assertThat(themes.size()).isEqualTo(6);
            assertThat(themes.get(0).getId()).isEqualTo(3L);
            assertThat(themes.get(0).getName()).isEqualTo("Art");
        }

        @Test
        @DisplayName("Succès - Récupérer toutes les catégories selon un thème par ordre alphabétique")
        public void shouldGetAllCategoriesByTheme() {
            // Given
            long themeId = 1L;

            // When
            List<IdAndNameProjection> categories = themeService.getCategoriesByTheme(themeId);

            // Then
            assertThat(categories.size()).isEqualTo(3);
            assertThat(categories.get(0).getId()).isEqualTo(14L);
            assertThat(categories.get(0).getName()).isEqualTo("Mythologie & Religion");
        }

        @Test
        @DisplayName("Erreur - Récupérer toutes les catégories selon un thème - thème non trouvé")
        public void shouldFailToGetAllCategoriesByTheme_WhenNonExistingTheme() {
            // Given
            long themeId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.getCategoriesByTheme(themeId));
        }

        @Test
        @DisplayName("Succès - Récupérer un thème par son ID")
        public void shouldGetTheme_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            ThemeAdminDTO theme = themeService.findByIdForAdmin(idToFind);

            // Then
            assertThat(theme.name()).isEqualTo("Sciences humaines");
        }

        @Test
        @DisplayName("Erreur - Récupérer un thème par un ID - thème non trouvé")
        public void shouldFailToGetTheme_WhenNonExistingId() {
            // Given
            long idToFind = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.findByIdForAdmin(idToFind));
        }

    }


    @Nested
    @DisplayName("Création de thèmes")
    class CreatingTheme {

        @Test
        @DisplayName("Succès")
        public void shouldCreate_WhenNonExistingTheme() {
            // Given
            ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO(" theme", " description");

            // When
            ThemeAdminDTO createdTheme = themeService.create(themeToCreate);

            // Then
            Theme theme = themeRepository.findById(createdTheme.id()).get();
            assertThat(theme.getName()).isEqualTo("Theme");
            assertThat(theme.getDescription()).isEqualTo("Description");
            assertThat(theme.getCreatedAt()).isNotNull();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void shouldFailToCreate_WhenExistingTheme() {
            // Given
            ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO(" art", "");

            // When / Then
            assertThrows(AlreadyExistException.class, () -> themeService.create(themeToCreate));
        }

    }


    @Nested
    @DisplayName("Mise à jour de thèmes")
    class UpdatingTheme {

        @Test
        @DisplayName("Succès")
        public void shouldUpdate_WhenExistingTheme() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO(" theme", " description");

            // When
            ThemeAdminDTO updatedTheme = themeService.update(1L, themeToUpdate);

            // Then
            Theme theme = themeRepository.findById(updatedTheme.id()).get();
            assertThat(theme.getName()).isEqualTo("Theme");
            assertThat(theme.getDescription()).isEqualTo("Description");
            assertThat(theme.getCreatedAt()).isNotNull();
            assertThat(theme.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void shouldFailToUpdate_WhenNonExistingTheme() {
            // Given
            ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO(" nouveau nom", "");

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.update(9999L, themeToUpdate));
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
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
        @DisplayName("Succès")
        public void shouldDelete_WhenExistingTheme() {
            // Given
            long idToDelete = 6L;

            // When
            themeService.delete(idToDelete);

            // Then
            assertThat(themeRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Erreur - Quiz restant")
        public void shouldFailToDelete_WhenExistingThemeWithQuizzes() {
            // Given
            long idToDelete = 1L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> themeService.delete(idToDelete));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void shouldFailToDelete_WhenNonExistingTheme() {
            // Given
            long idToDelete = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.delete(idToDelete));
        }

    }


    @Nested
    @DisplayName("Désactivation / réactivation de thèmes")
    class DisablingTheme {

        @Test
        @DisplayName("Succès - Désactivation")
        public void shouldDisable_WhenActiveTheme() {
            // Given
            long idToDisable = 4L;

            // When
            themeService.updateVisibility(idToDisable, false);

            // Then
            Theme theme = themeRepository.findById(idToDisable).get();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Réactivation")
        public void shouldEnable_WhenDisabledTheme() {
            // Given
            long idToEnable = 2L;

            // When
            themeService.updateVisibility(idToEnable, true);

            // Then
            Theme theme = themeRepository.findById(idToEnable).get();
            assertThat(theme.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void shouldFailToDisable_WhenNonExistingTheme() {
            // Given
            long idToDisable = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> themeService.updateVisibility(idToDisable, true));
        }

    }

}
