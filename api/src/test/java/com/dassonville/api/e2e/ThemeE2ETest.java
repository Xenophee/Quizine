package com.dassonville.api.e2e;

import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ThemeRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("E2E - Thème")
public class ThemeE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ThemeRepository themeRepository; // Pour vérifier la BDD après insertion

    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Créer et récupérer le thème avec le header Location")
    public void shouldCreateAndRetrieveTheme() {
        // Given
        ThemeUpsertDTO themeToCreate = new ThemeUpsertDTO("Cinéma", "Pour les cinéphiles");
        String url = "http://localhost:" + port + ApiRoutes.Themes.ADMIN_THEMES;

        // When - Envoi de la requête HTTP pour créer le thème
        HttpEntity<ThemeUpsertDTO> request = new HttpEntity<>(themeToCreate);
        ResponseEntity<ThemeAdminDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, ThemeAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        URI location = response.getHeaders().getLocation();
        assertThat(location).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().name()).isEqualTo(themeToCreate.name());

        // Vérifie que le thème est bien stocké en base
        boolean themeIsExists = themeRepository.existsByNameIgnoreCase(themeToCreate.name());
        assertThat(themeIsExists).isTrue();

        // Vérifie qu'on peut récupérer le thème avec un GET
        ResponseEntity<ThemePublicDTO> getResponse = restTemplate.getForEntity(location, ThemePublicDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().name()).isEqualTo(themeToCreate.name());
    }

    @Test
    @DisplayName("Mettre à jour un thème")
    public void shouldUpdateTheme() {
        // Given
        long id = 1;
        ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("Cinéma", "Pour les cinéphiles");
        String url = "http://localhost:" + port + ApiRoutes.Themes.ADMIN_THEMES + "/" + id;

        // When - Envoi de la requête HTTP pour mettre à jour le thème
        HttpEntity<ThemeUpsertDTO> requestUpdate = new HttpEntity<>(themeToUpdate);
        ResponseEntity<ThemeAdminDTO> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, ThemeAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        assertThat(responseUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseUpdate.getBody().name()).isEqualTo(themeToUpdate.name());

        // Vérifie que le thème est bien mis à jour en base
        Optional<Theme> theme = themeRepository.findById(id);
        assertThat(theme.get().getName()).isEqualTo(themeToUpdate.name());
    }

    @Test
    @DisplayName("Mettre à jour un thème inexistant")
    public void shouldFailToUpdateNonExistingTheme() {
        // Given
        long id = 9;
        ThemeUpsertDTO themeToUpdate = new ThemeUpsertDTO("Cinéma", "Pour les cinéphiles");
        String url = "http://localhost:" + port + ApiRoutes.Themes.ADMIN_THEMES + "/" + id;

        // When - Envoi de la requête HTTP pour mettre à jour le thème
        HttpEntity<ThemeUpsertDTO> requestUpdate = new HttpEntity<>(themeToUpdate);
        ResponseEntity<Error> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Error.class);

        // Then - Vérifie la réponse HTTP
        assertThat(responseUpdate.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseUpdate.getBody().getMessage()).isNotEmpty();
    }
}


