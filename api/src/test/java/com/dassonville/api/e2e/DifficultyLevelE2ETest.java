package com.dassonville.api.e2e;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.repository.DifficultyLevelRepository;
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
@DisplayName("E2E - Difficulty Level")
public class DifficultyLevelE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;


    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    @DisplayName("Créer et récupérer un niveau de difficulté")
    public void shouldCreateAndRetrieveDifficultyLevel() {
        // Given
        DifficultyLevelUpsertDTO difficultyLevelToCreate = new DifficultyLevelUpsertDTO("Volcanique", (byte) 5, (short) 0, 50);
        String url = "http://localhost:" + port + ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS;

        // When - Envoi de la requête HTTP pour créer le thème
        HttpEntity<DifficultyLevelUpsertDTO> request = new HttpEntity<>(difficultyLevelToCreate);
        ResponseEntity<DifficultyLevelAdminDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, DifficultyLevelAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        URI location = response.getHeaders().getLocation();
        assertThat(location).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().name()).isEqualTo(difficultyLevelToCreate.name());

        // Vérifie que le niveau de difficulté a été créé dans la base de données
        boolean difficultyLevelIsExists = difficultyLevelRepository.existsByNameIgnoreCase(difficultyLevelToCreate.name());
        assertThat(difficultyLevelIsExists).isTrue();

        // Vérifie qu'on peut récupérer le niveau de difficulté avec un GET
        ResponseEntity<DifficultyLevelAdminDTO> getResponse = restTemplate.getForEntity(location, DifficultyLevelAdminDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().name()).isEqualTo(difficultyLevelToCreate.name());
    }

    @Test
    @DisplayName("Mettre à jour un niveau de difficulté")
    public void shouldUpdateDifficultyLevel() {
        // Given
        long id = 1;
        DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO("Volcanique", (byte) 5, (short) 0, 50);
        String url = "http://localhost:" + port + ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS + "/" + id;

        // When - Envoi de la requête HTTP pour créer le thème
        HttpEntity<DifficultyLevelUpsertDTO> requestUpdate = new HttpEntity<>(difficultyLevelToUpdate);
        ResponseEntity<DifficultyLevelAdminDTO> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, DifficultyLevelAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        assertThat(responseUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseUpdate.getBody().name()).isEqualTo(difficultyLevelToUpdate.name());

        // Vérifie que le niveau de difficulté a bien été mis à jour dans la base de données
        Optional<DifficultyLevel> difficultyLevel = difficultyLevelRepository.findById(id);
        assertThat(difficultyLevel.get().getName()).isEqualTo(difficultyLevelToUpdate.name());
    }

    @Test
    @DisplayName("Mettre à jour un niveau de difficulté inexistant")
    public void shouldFailToUpdateNonExistingDifficultyLevel() {
        // Given
        long id = 9;
        DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO("volcanique", (byte) 5, (short) 0, 50);
        String url = "http://localhost:" + port + ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS + "/" + id;

        // When - Envoi de la requête HTTP pour créer le thème
        HttpEntity<DifficultyLevelUpsertDTO> requestUpdate = new HttpEntity<>(difficultyLevelToUpdate);
        ResponseEntity<Error> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Error.class);

        // Then - Vérifie la réponse HTTP
        assertThat(responseUpdate.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseUpdate.getBody().getMessage()).isNotEmpty();
    }
}
