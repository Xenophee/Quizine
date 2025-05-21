package com.dassonville.api.service;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.DifficultyLevelMapper;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Niveaux de difficulté")
public class DifficultyLevelServiceTest {

    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;

    private DifficultyLevelService difficultyLevelService;

    private final DifficultyLevelMapper difficultyLevelMapper = Mappers.getMapper(DifficultyLevelMapper.class);


    private long id;
    private DifficultyLevel difficultyLevel;
    private DifficultyLevelUpsertDTO difficultyLevelUpsertDTO;


    @BeforeEach
    void setUp() {
        difficultyLevelService = new DifficultyLevelService(difficultyLevelRepository, difficultyLevelMapper);

        id = 1L;

        difficultyLevel = new DifficultyLevel();
        difficultyLevel.setId(1);
        difficultyLevel.setName("Facile");
        difficultyLevel.setMaxAnswers((byte) 2);
        difficultyLevel.setTimerSeconds((short) 0);
        difficultyLevel.setPointsPerQuestion(5);
        difficultyLevel.setIsReference(false);
        difficultyLevel.setCreatedAt(LocalDateTime.now());

        difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO(" facile", (byte) 2, (short) 0, 5);
    }


    @Nested
    @DisplayName("Récupérer tous les niveaux de difficulté")
    class GetAllDifficultyLevelsTest {

        @Test
        @DisplayName("ADMIN - Récupérer tous les niveaux de difficulté")
        void getAllDifficultyLevels() {
            // Given
            when(difficultyLevelRepository.findAllByOrderByDisplayOrder())
                    .thenReturn(List.of(difficultyLevel));

            // When
            List<DifficultyLevelAdminDTO> results = difficultyLevelService.getAllDifficultyLevels();

            // Then
            verify(difficultyLevelRepository).findAllByOrderByDisplayOrder();
            
            assertThat(results).isNotNull();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.get(0).name()).isEqualTo(difficultyLevel.getName());
            assertThat(results.get(0).maxAnswers()).isEqualTo(difficultyLevel.getMaxAnswers());
            assertThat(results.get(0).timerSeconds()).isEqualTo(difficultyLevel.getTimerSeconds());
            assertThat(results.get(0).pointsPerQuestion()).isEqualTo(difficultyLevel.getPointsPerQuestion());
            assertThat(results.get(0).createdAt()).isEqualTo(difficultyLevel.getCreatedAt());
            assertThat(results.get(0).isReference()).isEqualTo(difficultyLevel.getIsReference());
        }

        @Test
        @DisplayName("PUBLIC - Récupérer tous les niveaux de difficulté actifs")
        void getAllActiveDifficultyLevels() {
            PublicDifficultyLevelProjection projection = mock(PublicDifficultyLevelProjection.class);
            when(projection.getId()).thenReturn(1L);
            when(projection.getName()).thenReturn("Facile");
            when(projection.getMaxAnswers()).thenReturn((byte) 0);
            when(projection.getTimerSeconds()).thenReturn((short) 0);
            when(projection.getPointsPerQuestion()).thenReturn(5);
            when(projection.getCreatedAt()).thenReturn(LocalDateTime.now());

            // Given
            when(difficultyLevelRepository.findByDisabledAtIsNullOrderByDisplayOrder())
                    .thenReturn(List.of(projection));

            // When
            List<DifficultyLevelPublicDTO> results = difficultyLevelService.getAllActiveDifficultyLevels();

            // Then
            verify(difficultyLevelRepository).findByDisabledAtIsNullOrderByDisplayOrder();

            assertThat(results).isNotNull();
            assertThat(results.size()).isEqualTo(1);
            assertThat(results.get(0).id()).isEqualTo(projection.getId());
            assertThat(results.get(0).name()).isEqualTo(projection.getName());
            assertThat(results.get(0).maxAnswers()).isEqualTo(projection.getMaxAnswers());
            assertThat(results.get(0).timerSeconds()).isEqualTo(projection.getTimerSeconds());
            assertThat(results.get(0).pointsPerQuestion()).isEqualTo(projection.getPointsPerQuestion());
            assertThat(results.get(0).isNew()).isTrue();
        }
    }


    @Nested
    @DisplayName("Trouver un niveau de difficulté par son ID")
    class FindByIdTest {

        @Test
        @DisplayName("Succès")
        void findById_existingId() {
            // Given
            when(difficultyLevelRepository.findById(id))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.findById(id);

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
            
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevel.getName());
            assertThat(result.maxAnswers()).isEqualTo(difficultyLevel.getMaxAnswers());
            assertThat(result.timerSeconds()).isEqualTo(difficultyLevel.getTimerSeconds());
            assertThat(result.pointsPerQuestion()).isEqualTo(difficultyLevel.getPointsPerQuestion());
            assertThat(result.createdAt()).isEqualTo(difficultyLevel.getCreatedAt());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void findById_nonExistingId() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.findById(id));

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
        }
    }


    @Nested
    @DisplayName("Créer un niveau de difficulté")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(false);
            when(difficultyLevelRepository.save(any(DifficultyLevel.class)))
                    .thenReturn(difficultyLevel);

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.create(difficultyLevelUpsertDTO);

            // Then
            verify(difficultyLevelRepository).existsByNameIgnoreCase(anyString());
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevel.getName());
            assertThat(result.maxAnswers()).isEqualTo(difficultyLevel.getMaxAnswers());
            assertThat(result.timerSeconds()).isEqualTo(difficultyLevel.getTimerSeconds());
            assertThat(result.pointsPerQuestion()).isEqualTo(difficultyLevel.getPointsPerQuestion());
            assertThat(result.createdAt()).isEqualTo(difficultyLevel.getCreatedAt());
        }

        @Test
        @DisplayName("Erreur - Nom déjà existant")
        void create_existingName() {
            // Given
            when(difficultyLevelRepository.existsByNameIgnoreCase(anyString()))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.create(difficultyLevelUpsertDTO));

            verify(difficultyLevelRepository).existsByNameIgnoreCase(anyString());
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }


    @Nested
    @DisplayName("Mettre à jour un niveau de difficulté")
    class UpdateTest {

        @Test
        @DisplayName("Succès")
        void update_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));
            when(difficultyLevelRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(difficultyLevelRepository.save(any(DifficultyLevel.class)))
                    .thenReturn(difficultyLevel);

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.update(id, difficultyLevelUpsertDTO);

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));

            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevel.getName());
            assertThat(result.maxAnswers()).isEqualTo(difficultyLevel.getMaxAnswers());
            assertThat(result.timerSeconds()).isEqualTo(difficultyLevel.getTimerSeconds());
            assertThat(result.pointsPerQuestion()).isEqualTo(difficultyLevel.getPointsPerQuestion());
            assertThat(result.createdAt()).isEqualTo(difficultyLevel.getCreatedAt());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void update_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.update(id, difficultyLevelUpsertDTO));

            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository, never()).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("Erreur - Nom déjà existant")
        void update_existingDifficultyLevelWithExistingName() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));
            when(difficultyLevelRepository.existsByNameIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.update(id, difficultyLevelUpsertDTO));

            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository).existsByNameIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }


    @Nested
    @DisplayName("Supprimer un niveau de difficulté")
    class DeleteTest {

        @Test
        @DisplayName("Succès")
        void delete_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            difficultyLevelService.delete(id);

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void delete_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.delete(id));

            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository, never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Impossible de supprimer un niveau de difficulté de référence")
        void delete_referenceDifficultyLevel() {
            // Given
            difficultyLevel.setIsReference(true);
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));

            // When & Then
            assertThrows(ActionNotAllowedException.class, () -> difficultyLevelService.delete(id));

            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository, never()).deleteById(anyLong());
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'un niveau de difficulté")
    class UpdateVisibilityTest {

        @Test
        @DisplayName("Succès")
        void disable_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            difficultyLevelService.updateVisibility(id, false);

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("RAS - Niveau de difficulté déjà activé")
        void updateVisibility_alreadyDisabled() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            difficultyLevelService.updateVisibility(id, true);

            // Then
            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void updateVisibility_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.updateVisibility(id, false));

            verify(difficultyLevelRepository).findById(anyLong());
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }


    @Nested
    @DisplayName("Changer l'ordre d'affichage")
    class UpdateDisplayOrderTest {

        @Test
        @DisplayName("Succès")
        void updateDisplayOrder_success() {
            // Given
            List<Long> newOrder = List.of(1L, 2L, 3L);
            List<DifficultyLevel> levels = List.of(
                    new DifficultyLevel(1L, "Facile", (short) 1),
                    new DifficultyLevel(2L, "Moyen", (short) 2),
                    new DifficultyLevel(3L, "Difficile", (short) 3)
            );

            when(difficultyLevelRepository.findAllById(newOrder)).thenReturn(levels);

            // When
            difficultyLevelService.updateDisplayOrder(newOrder);

            // Then
            verify(difficultyLevelRepository).findAllById(newOrder);
            verify(difficultyLevelRepository, times(2)).saveAll(levels);
            verify(difficultyLevelRepository).flush();

            assertThat(levels.get(0).getDisplayOrder()).isEqualTo((short) 1);
            assertThat(levels.get(1).getDisplayOrder()).isEqualTo((short) 2);
            assertThat(levels.get(2).getDisplayOrder()).isEqualTo((short) 3);
        }

        @Test
        @DisplayName("Erreur - Certains IDs fournis n'existent pas")
        void updateDisplayOrder_idsNotFound() {
            // Given
            List<Long> newOrder = List.of(1L, 2L, 3L);
            List<DifficultyLevel> levels = List.of(
                    new DifficultyLevel(1L, "Facile", (short) 1),
                    new DifficultyLevel(2L, "Moyen", (short) 2)
            );

            when(difficultyLevelRepository.findAllById(newOrder)).thenReturn(levels);

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.updateDisplayOrder(newOrder));

            verify(difficultyLevelRepository).findAllById(newOrder);
            verify(difficultyLevelRepository, never()).saveAll(anyList());
            verify(difficultyLevelRepository, never()).flush();
        }
    }
}
