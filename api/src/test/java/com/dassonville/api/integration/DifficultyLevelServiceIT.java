package com.dassonville.api.integration;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.service.DifficultyLevelService;
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
@DisplayName("IT - DifficultyLevelService")
public class DifficultyLevelServiceIT {

    @Autowired
    private DifficultyLevelService difficultyLevelService;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Nested
    @DisplayName("Récupération de niveaux de difficulté")
    class GettingThemes {

        @Test
        @DisplayName("Récupérer tous les niveaux de difficulté")
        public void shouldGetAllDifficultyLevels() {
            // When
            List<DifficultyLevelAdminDTO> difficultyLevels = difficultyLevelService.getAllDifficultyLevels();

            // Then
            assertThat(difficultyLevels.size()).isEqualTo(4);
        }

        @Test
        @DisplayName("Récupérer tous les niveaux de difficulté actifs")
        public void shouldGetAllActiveDifficultyLevels() {
            // When
            List<DifficultyLevelPublicDTO> difficultyLevels = difficultyLevelService.getAllActiveDifficultyLevels();

            // Then
            assertThat(difficultyLevels.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("Récupérer un niveau de difficulté par son ID")
        public void shouldGetDifficultyLevel_WhenExistingId() {
            // Given
            long idToFind = 1L;

            // When
            DifficultyLevelAdminDTO difficultyLevel = difficultyLevelService.findById(idToFind);

            // Then
            assertThat(difficultyLevel.name()).isEqualTo("Facile");
        }

        @Test
        @DisplayName("Récupérer un niveau de difficulté par un ID inexistant")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            long idToFind = 100L;

            // When / Then
            assertThrows(Exception.class, () -> difficultyLevelService.findById(idToFind));
        }
    }


    @Nested
    @DisplayName("Création de niveaux de difficulté")
    class CreatingDifficultyLevels {

        @Test
        @DisplayName("Créer un niveau de difficulté")
        public void shouldCreateDifficultyLevel() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToCreate = new DifficultyLevelUpsertDTO(
                    "volcanique",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When
            DifficultyLevelAdminDTO createdDifficultyLevel = difficultyLevelService.create(difficultyLevelToCreate);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(createdDifficultyLevel.id()).get();
            assertThat(difficultyLevel.getName()).isEqualTo(capitalize(difficultyLevelToCreate.name()));
            assertThat(difficultyLevel.getCreatedAt()).isNotNull();
            assertThat(difficultyLevel.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Créer un niveau de difficulté avec un nom déjà existant")
        public void shouldThrowAlreadyExist_WhenNameAlreadyExists() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToCreate = new DifficultyLevelUpsertDTO(
                    "facile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.create(difficultyLevelToCreate));
        }
    }


    @Nested
    @DisplayName("Mise à jour de niveaux de difficulté")
    class UpdatingDifficultyLevels {

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté existant")
        public void shouldUpdateDifficultyLevel() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    "facile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When
            DifficultyLevelAdminDTO updatedDifficultyLevel = difficultyLevelService.update(1L, difficultyLevelToUpdate);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(updatedDifficultyLevel.id()).get();
            assertThat(difficultyLevel.getName()).isEqualTo(capitalize(difficultyLevelToUpdate.name()));
            assertThat(difficultyLevel.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté inexistant")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    "facile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.update(100L, difficultyLevelToUpdate));
        }

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté avec un nom déjà existant")
        public void shouldThrowAlreadyExist_WhenNameAlreadyExists() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    "difficile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.update(1L, difficultyLevelToUpdate));
        }
    }

    @Nested
    @DisplayName("Suppression de niveaux de difficulté")
    class DeletingDifficultyLevels {

        @Test
        @DisplayName("Supprimer un niveau de difficulté existant")
        public void shouldDeleteDifficultyLevel() {
            // Given
            long idToDelete = 1L;

            // When
            difficultyLevelService.delete(idToDelete);

            // Then
            assertThat(difficultyLevelRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Supprimer un niveau de difficulté inexistant")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            long idToDelete = 100L;

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.delete(idToDelete));
        }
    }

    @Nested
    @DisplayName("Désactivation / réactivation de niveaux de difficulté")
    class DisablingDifficultyLevels {

        @Test
        @DisplayName("Désactiver un niveau de difficulté existant")
        public void shouldDisableDifficultyLevel() {
            // Given
            long idToDisable = 1L;
            ToggleDisableRequestDTO toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);

            // When
            difficultyLevelService.toggleDisable(idToDisable, toggleDisableRequestDTO);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(idToDisable).get();
            assertThat(difficultyLevel.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Réactiver un niveau de difficulté existant")
        public void shouldEnableDifficultyLevel() {
            // Given
            long idToEnable = 1L;
            ToggleDisableRequestDTO toggleDisableRequestDTO = new ToggleDisableRequestDTO(false);

            // When
            difficultyLevelService.toggleDisable(idToEnable, toggleDisableRequestDTO);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(idToEnable).get();
            assertThat(difficultyLevel.getDisabledAt()).isNull();
        }
    }

}
