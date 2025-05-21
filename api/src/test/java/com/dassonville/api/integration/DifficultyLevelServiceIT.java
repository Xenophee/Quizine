package com.dassonville.api.integration;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
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


@SpringBootTest
@DisplayName("IT - Service : Niveau de difficulté")
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
        @DisplayName("Succès - Récupérer tous les niveaux de difficulté")
        public void shouldGetAllDifficultyLevels() {
            // When
            List<DifficultyLevelAdminDTO> difficultyLevels = difficultyLevelService.getAllDifficultyLevels();

            // Then
            assertThat(difficultyLevels.size()).isEqualTo(4);
            assertThat(difficultyLevels.get(0).displayOrder()).isEqualTo((byte) 1);
            assertThat(difficultyLevels.get(1).displayOrder()).isEqualTo((byte) 2);
            assertThat(difficultyLevels.get(2).displayOrder()).isEqualTo((byte) 3);
            assertThat(difficultyLevels.get(3).displayOrder()).isEqualTo((byte) 4);
        }

        @Test
        @DisplayName("Succès - Récupérer tous les niveaux de difficulté actifs")
        public void shouldGetAllActiveDifficultyLevels() {
            // When
            List<DifficultyLevelPublicDTO> difficultyLevels = difficultyLevelService.getAllActiveDifficultyLevels();

            // Then
            assertThat(difficultyLevels.size()).isEqualTo(2);
            assertThat(difficultyLevels.get(0).id()).isEqualTo(3L);
            assertThat(difficultyLevels.get(1).id()).isEqualTo(2L);

            assertThat(difficultyLevels.get(0).name()).isEqualTo("Facile");
            assertThat(difficultyLevels.get(0).maxAnswers()).isEqualTo((byte) 2);
            assertThat(difficultyLevels.get(0).timerSeconds()).isEqualTo((short) 0);
            assertThat(difficultyLevels.get(0).pointsPerQuestion()).isEqualTo(5);
            assertThat(difficultyLevels.get(0).isNew()).isTrue();

            assertThat(difficultyLevels.get(1).name()).isEqualTo("Intermédiaire");
            assertThat(difficultyLevels.get(1).isNew()).isFalse();
        }

        @Test
        @DisplayName("Succès - Récupérer un niveau de difficulté par son ID")
        public void shouldGetDifficultyLevel_WhenExistingId() {
            // Given
            long idToFind = 3L;

            // When
            DifficultyLevelAdminDTO difficultyLevel = difficultyLevelService.findById(idToFind);

            // Then
            assertThat(difficultyLevel.name()).isEqualTo("Facile");
            assertThat(difficultyLevel.maxAnswers()).isEqualTo((byte) 2);
            assertThat(difficultyLevel.timerSeconds()).isEqualTo((short) 0);
            assertThat(difficultyLevel.pointsPerQuestion()).isEqualTo(5);
            assertThat(difficultyLevel.isReference()).isFalse();
            assertThat(difficultyLevel.createdAt()).isNotNull();
            assertThat(difficultyLevel.disabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Récupérer un niveau de difficulté par son ID - Niveau de difficulté non trouvé")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            long idToFind = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.findById(idToFind));
        }
    }


    @Nested
    @DisplayName("Création de niveaux de difficulté")
    class CreatingDifficultyLevels {

        @Test
        @DisplayName("Succès")
        public void shouldCreateDifficultyLevel() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToCreate = new DifficultyLevelUpsertDTO(
                    " volcanique",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When
            DifficultyLevelAdminDTO createdDifficultyLevel = difficultyLevelService.create(difficultyLevelToCreate);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(createdDifficultyLevel.id()).get();
            assertThat(difficultyLevel.getName()).isEqualTo("Volcanique");
            assertThat(difficultyLevel.getMaxAnswers()).isEqualTo(createdDifficultyLevel.maxAnswers());
            assertThat(difficultyLevel.getTimerSeconds()).isEqualTo(createdDifficultyLevel.timerSeconds());
            assertThat(difficultyLevel.getPointsPerQuestion()).isEqualTo(createdDifficultyLevel.pointsPerQuestion());
            assertThat(difficultyLevel.getIsReference()).isFalse();
            assertThat(difficultyLevel.getDisplayOrder()).isEqualTo((byte) 5);
            assertThat(difficultyLevel.getCreatedAt()).isNotNull();
            assertThat(difficultyLevel.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté déjà existant")
        public void shouldThrowAlreadyExist_WhenNameAlreadyExists() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToCreate = new DifficultyLevelUpsertDTO(
                    " facile",
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
        @DisplayName("Succès")
        public void shouldUpdateDifficultyLevel() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    " facile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When
            DifficultyLevelAdminDTO updatedDifficultyLevel = difficultyLevelService.update(3L, difficultyLevelToUpdate);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(updatedDifficultyLevel.id()).get();
            assertThat(difficultyLevel.getName()).isEqualTo("Facile");
            assertThat(difficultyLevel.getMaxAnswers()).isEqualTo(updatedDifficultyLevel.maxAnswers());
            assertThat(difficultyLevel.getTimerSeconds()).isEqualTo(updatedDifficultyLevel.timerSeconds());
            assertThat(difficultyLevel.getPointsPerQuestion()).isEqualTo(updatedDifficultyLevel.pointsPerQuestion());
            assertThat(difficultyLevel.getIsReference()).isFalse();
            assertThat(difficultyLevel.getDisplayOrder()).isEqualTo((byte) 1);
            assertThat(difficultyLevel.getCreatedAt()).isNotNull();
            assertThat(difficultyLevel.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    " facile",
                    (byte) 5,
                    (short) 30,
                    10
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.update(100L, difficultyLevelToUpdate));
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté déjà existant")
        public void shouldThrowAlreadyExist_WhenNameAlreadyExists() {
            // Given
            DifficultyLevelUpsertDTO difficultyLevelToUpdate = new DifficultyLevelUpsertDTO(
                    " difficile",
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
        @DisplayName("Succès")
        public void shouldDeleteDifficultyLevel() {
            // Given
            long idToDelete = 1L;

            // When
            difficultyLevelService.delete(idToDelete);

            // Then
            assertThat(difficultyLevelRepository.existsById(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            long idToDelete = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.delete(idToDelete));
        }

        @Test
        @DisplayName("Erreur - Action non autorisée : Niveau de difficulté référencé")
        public void shouldThrowActionNotAllowed_WhenReferenced() {
            // Given
            long idToDelete = 2L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> difficultyLevelService.delete(idToDelete));
        }
    }


    @Nested
    @DisplayName("Désactivation / réactivation de niveaux de difficulté")
    class updateVisibilityDifficultyLevels {

        @Test
        @DisplayName("Succès - Désactiver un niveau de difficulté")
        public void shouldDisableDifficultyLevel() {
            // Given
            long idToDisable = 1L;

            // When
            difficultyLevelService.updateVisibility(idToDisable, false);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(idToDisable).get();
            assertThat(difficultyLevel.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Réactiver un niveau de difficulté")
        public void shouldEnableDifficultyLevel() {
            // Given
            long idToEnable = 1L;

            // When
            difficultyLevelService.updateVisibility(idToEnable, true);

            // Then
            DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(idToEnable).get();
            assertThat(difficultyLevel.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        public void shouldThrowNotFound_WhenNotExistingId() {
            // Given
            long idToDisable = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.updateVisibility(idToDisable, false));
        }
    }


    @Nested
    @DisplayName("Mise à jour de l'ordre d'affichage")
    class UpdateDisplayOrder {

        @Test
        @DisplayName("Succès")
        public void shouldUpdateDisplayOrder() {
            // Given
            List<Long> newOrder = List.of(1L, 4L, 2L, 3L);

            // When
            difficultyLevelService.updateDisplayOrder(newOrder);

            // Then
            List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAllById(newOrder);
            assertThat(difficultyLevels.get(0).getDisplayOrder()).isEqualTo((short) 1);
            assertThat(difficultyLevels.get(1).getDisplayOrder()).isEqualTo((short) 3);
            assertThat(difficultyLevels.get(2).getDisplayOrder()).isEqualTo((short) 4);
            assertThat(difficultyLevels.get(3).getDisplayOrder()).isEqualTo((short) 2);
        }

        @Test
        @DisplayName("Erreur - IDs non trouvés")
        public void shouldThrowNotFound_WhenIdsNotFound() {
            // Given
            List<Long> newOrder = List.of(100L, 200L, 3L, 4L);

            // When / Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.updateDisplayOrder(newOrder));
        }
    }

}
