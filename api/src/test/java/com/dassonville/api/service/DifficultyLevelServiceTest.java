package com.dassonville.api.service;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - DifficultyLevelService")
public class DifficultyLevelServiceTest {

    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;

    private DifficultyLevelService difficultyLevelService;

    private final DifficultyLevelMapper difficultyLevelMapper = Mappers.getMapper(DifficultyLevelMapper.class);


    private long id;
    private ToggleDisableRequestDTO toggleDisableRequestDTO;
    private DifficultyLevel difficultyLevel;
    private PublicDifficultyLevelProjection publicDifficultyLevelProjection;
    private DifficultyLevel difficultyLevelToUpdate;
    private DifficultyLevelAdminDTO difficultyLevelAdminDTO;
    private DifficultyLevelUpsertDTO difficultyLevelToCreateDTO;
    private DifficultyLevelUpsertDTO difficultyLevelToUpdateDTO;


    @BeforeEach
    void setUp() {
        difficultyLevelService = new DifficultyLevelService(difficultyLevelRepository, difficultyLevelMapper);

        id = 1L;
        toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);

        difficultyLevel = new DifficultyLevel();
        difficultyLevel.setId(1);
        difficultyLevel.setName("facile");
        difficultyLevel.setMaxResponses((byte) 2);
        difficultyLevel.setTimerSeconds((short) 0);
        difficultyLevel.setPointsPerQuestion(5);
        difficultyLevel.setCreatedAt(LocalDate.now());

        difficultyLevelToUpdate = new DifficultyLevel();
        difficultyLevelToUpdate.setId(1);
        difficultyLevelToUpdate.setName("Intermédiaire");
        difficultyLevelToUpdate.setMaxResponses((byte) 4);
        difficultyLevelToUpdate.setTimerSeconds((short) 0);
        difficultyLevelToUpdate.setPointsPerQuestion(10);

        difficultyLevelAdminDTO = difficultyLevelMapper.toAdminDTO(difficultyLevel);
        difficultyLevelToCreateDTO = difficultyLevelMapper.toUpsertDTO(difficultyLevel);
        difficultyLevelToUpdateDTO = difficultyLevelMapper.toUpsertDTO(difficultyLevelToUpdate);
    }


    @Nested
    @DisplayName("Test de la méthode getAllDifficultyLevels")
    class GetAllDifficultyLevelsTest {

        @Test
        @DisplayName("Récupérer tous les niveaux de difficulté")
        void getAllDifficultyLevels() {
            // Given
            when(difficultyLevelRepository.findAll())
                    .thenReturn(List.of(difficultyLevel));

            // When
            difficultyLevelService.getAllDifficultyLevels();

            // Then
            verify(difficultyLevelRepository).findAll();
        }

        @Test
        @DisplayName("Récupérer tous les niveaux de difficulté actifs")
        void getAllActiveDifficultyLevels() {
            publicDifficultyLevelProjection = mock(PublicDifficultyLevelProjection.class);
            when(publicDifficultyLevelProjection.getId()).thenReturn(1L);
            when(publicDifficultyLevelProjection.getName()).thenReturn("facile");
            when(publicDifficultyLevelProjection.getMaxResponses()).thenReturn((byte) 0);
            when(publicDifficultyLevelProjection.getTimerSeconds()).thenReturn((short) 0);
            when(publicDifficultyLevelProjection.getPointsPerQuestion()).thenReturn(5);
            when(publicDifficultyLevelProjection.getCreatedAt()).thenReturn(LocalDate.now());

            // Given
            when(difficultyLevelRepository.findByDisabledAtIsNull())
                    .thenReturn(List.of(publicDifficultyLevelProjection));

            // When
            difficultyLevelService.getAllActiveDifficultyLevels();

            // Then
            verify(difficultyLevelRepository).findByDisabledAtIsNull();
        }
    }

    @Nested
    @DisplayName("Test de la méthode findById")
    class FindByIdTest {

        @Test
        @DisplayName("Trouver un niveau de difficulté par ID")
        void findById_existingId() {
            // Given
            when(difficultyLevelRepository.findById(id))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.findById(id);

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevelAdminDTO.name());
        }

        @Test
        @DisplayName("Trouver un niveau de difficulté par un ID inexistant")
        void findById_nonExistingId() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.findById(id));

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
        }
    }

    @Nested
    @DisplayName("Tests de la méthode create")
    class CreateTest {

        @Test
        @DisplayName("Créer un niveau de difficulté")
        void create_newDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(difficultyLevelRepository.save(any(DifficultyLevel.class)))
                    .thenReturn(difficultyLevel);

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.create(difficultyLevelToCreateDTO);

            // Then
            verify(difficultyLevelRepository).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevelAdminDTO.name());
        }

        @Test
        @DisplayName("Créer un niveau de difficulté avec un nom déjà existant")
        void create_existingName() {
            // Given
            when(difficultyLevelRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.create(difficultyLevelToCreateDTO));

            verify(difficultyLevelRepository).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }

    @Nested
    @DisplayName("Tests de la méthode update")
    class UpdateTest {

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté")
        void update_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(difficultyLevel));
            when(difficultyLevelRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(difficultyLevelRepository.save(any(DifficultyLevel.class)))
                    .thenReturn(difficultyLevelToUpdate);

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.update(id, difficultyLevelToUpdateDTO);

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevelToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté sans changer le nom")
        void update_existingDifficultyLevelWithoutChangingName() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(difficultyLevelToUpdate));
            when(difficultyLevelRepository.save(any(DifficultyLevel.class)))
                    .thenReturn(difficultyLevelToUpdate);

            // When
            DifficultyLevelAdminDTO result = difficultyLevelService.update(id, difficultyLevelToUpdateDTO);

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(difficultyLevelToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté avec un ID inexistant")
        void update_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.update(id, difficultyLevelToUpdateDTO));

            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("Mettre à jour un niveau de difficulté avec un nom déjà existant")
        void update_existingDifficultyLevelWithExistingName() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(difficultyLevel));
            when(difficultyLevelRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> difficultyLevelService.update(id, difficultyLevelToUpdateDTO));

            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository).existsByNameIgnoreCase(any(String.class));
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }

    @Nested
    @DisplayName("Tests de la méthode delete")
    class DeleteTest {

        @Test
        @DisplayName("Supprimer un niveau de difficulté")
        void delete_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.existsById(any(Long.class)))
                    .thenReturn(true);

            // When
            difficultyLevelService.delete(id);

            // Then
            verify(difficultyLevelRepository).existsById(any(Long.class));
            verify(difficultyLevelRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("Supprimer un niveau de difficulté avec un ID inexistant")
        void delete_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.existsById(any(Long.class)))
                    .thenReturn(false);

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.delete(id));

            verify(difficultyLevelRepository).existsById(any(Long.class));
            verify(difficultyLevelRepository, never()).deleteById(any(Long.class));
        }
    }

    @Nested
    @DisplayName("Tests de la méthode toggleDisable")
    class ToggleDisableTest {

        @Test
        @DisplayName("Désactiver un niveau de difficulté")
        void disable_existingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            difficultyLevelService.toggleDisable(id, toggleDisableRequestDTO);

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("Réactiver un niveau de difficulté")
        void enable_existingDifficultyLevel() {
            // Given
            toggleDisableRequestDTO = new ToggleDisableRequestDTO(false);
            difficultyLevel.setDisabledAt(difficultyLevel.getCreatedAt());
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(difficultyLevel));

            // When
            difficultyLevelService.toggleDisable(id, toggleDisableRequestDTO);

            // Then
            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository).save(any(DifficultyLevel.class));
        }

        @Test
        @DisplayName("Désactiver un niveau de difficulté avec un ID inexistant")
        void toggleDisable_nonExistingDifficultyLevel() {
            // Given
            when(difficultyLevelRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> difficultyLevelService.toggleDisable(id, toggleDisableRequestDTO));

            verify(difficultyLevelRepository).findById(any(Long.class));
            verify(difficultyLevelRepository, never()).save(any(DifficultyLevel.class));
        }
    }
}
