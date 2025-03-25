package com.dassonville.api.e2e;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.model.Category;
import com.dassonville.api.repository.CategoryRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("E2E - Catégorie")
public class CategoryE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository; // Pour vérifier la BDD après insertion

    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    @DisplayName("Créer et récupérer la catégorie avec le header Location")
    public void shouldCreateAndRetrieveCategory() {
        // Given
        CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO("Droit européen", "Pour les juristes en herbe", 2);
        String url = "http://localhost:" + port + ApiRoutes.Categories.ADMIN_CATEGORIES;

        // When - Envoi de la requête HTTP pour créer la catégorie
        HttpEntity<CategoryUpsertDTO> request = new HttpEntity<>(categoryToCreate);
        ResponseEntity<CategoryAdminDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, CategoryAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        URI location = response.getHeaders().getLocation();
        assertThat(location).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().name()).isEqualTo(categoryToCreate.name());

        // Vérifie que la catégorie est bien stockée en base
        boolean categoryIsExists = categoryRepository.existsByName(categoryToCreate.name());
        assertThat(categoryIsExists).isTrue();

        // Vérifie qu'on peut récupérer la catégorie avec un GET
        ResponseEntity<CategoryAdminDTO> getResponse = restTemplate.getForEntity(location, CategoryAdminDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().name()).isEqualTo(categoryToCreate.name());
    }

    @Test
    @DisplayName("Créer une catégorie avec le même nom")
    public void shouldNotCreateCategoryWithSameName() {
        // Given
        CategoryUpsertDTO categoryToCreate = new CategoryUpsertDTO("Cinéma", "Pour les cinéphiles", 1);
        String url = "http://localhost:" + port + ApiRoutes.Categories.ADMIN_CATEGORIES;

        // When - Envoi de la requête HTTP pour créer la catégorie
        HttpEntity<CategoryUpsertDTO> request = new HttpEntity<>(categoryToCreate);
        ResponseEntity<Error> response = restTemplate.exchange(url, HttpMethod.POST, request, Error.class);

        // Then - Vérifie la réponse HTTP
        URI location = response.getHeaders().getLocation();
        assertThat(location).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMessage()).isNotEmpty();
    }

    @Test
    @DisplayName("Mettre à jour une catégorie")
    public void shouldUpdateCategory() {
        // Given
        long id = 4;
        CategoryUpsertDTO categoryToUpdate = new CategoryUpsertDTO("Droit européen", "Pour les juristes en herbe", 2);
        String url = "http://localhost:" + port + ApiRoutes.Categories.ADMIN_CATEGORIES + "/" + id;

        // When - Envoi de la requête HTTP pour mettre à jour la catégorie
        HttpEntity<CategoryUpsertDTO> requestUpdate = new HttpEntity<>(categoryToUpdate);
        ResponseEntity<CategoryAdminDTO> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, CategoryAdminDTO.class);

        // Then - Vérifie la réponse HTTP
        assertThat(responseUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseUpdate.getBody().name()).isEqualTo(categoryToUpdate.name());

        // Vérifie que la catégorie est bien mise à jour en base
        Optional<Category> category = categoryRepository.findById(id);
        assertThat(category.get().getName()).isEqualTo(categoryToUpdate.name());
    }
}
