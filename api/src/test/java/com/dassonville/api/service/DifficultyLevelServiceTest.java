package com.dassonville.api.service;


import com.dassonville.api.dto.response.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.response.DifficultyLevelPublicDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.MisconfiguredException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.DifficultyLevelMapper;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.util.TestPublicDifficultyLevelProjection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@Tag("service")
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Niveaux de difficulté")
public class DifficultyLevelServiceTest {

    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;
    @Mock
    private QuizRepository quizRepository;

    private DifficultyLevelService difficultyLevelService;

    private final DifficultyLevelMapper difficultyLevelMapper = Mappers.getMapper(DifficultyLevelMapper.class);


    private long id;
    private DifficultyLevel difficultyLevel;


    @BeforeEach
    void setUp() {
        difficultyLevelService = new DifficultyLevelService(difficultyLevelRepository, difficultyLevelMapper, quizRepository);

        id = 1L;

        difficultyLevel = DifficultyLevel.builder()
                .id(id)
                .name("Facile")
                .isReference(false)
                .label("FACILE")
                .description("Un niveau facile pour commencer.")
                .rank((byte) 1)
                .startsAt(null)
                .endsAt(null)
                .isRecurring(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .build();
    }


    @Nested
    @DisplayName("Récupérer tous les niveaux de difficulté")
    class GetAllDifficultyLevelsTest {

        @Test
        @DisplayName("ADMIN - Récupérer tous les niveaux de difficulté")
        void getAllDifficultyLevels() {
            // Given
            when(difficultyLevelRepository.findAllByOrderByRank())
                    .thenReturn(List.of(difficultyLevel));

            // When
            List<DifficultyLevelAdminDTO> results = difficultyLevelService.getAllDifficultyLevels();
            DifficultyLevelAdminDTO dto = results.getFirst();

            // Then
            assertAll("Verify method calls",
                    () -> verify(difficultyLevelRepository).findAllByOrderByRank()
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(results).isNotEmpty(),
                    () -> assertThat(results).hasSize(1),
                    () -> assertThat(dto.name()).isEqualTo(difficultyLevel.getName()),
                    () -> assertThat(dto.isReference()).isEqualTo(difficultyLevel.getIsReference()),
                    () -> assertThat(dto.label()).isEqualTo(difficultyLevel.getLabel()),
                    () -> assertThat(dto.description()).isEqualTo(difficultyLevel.getDescription()),
                    () -> assertThat(dto.rank()).isEqualTo(difficultyLevel.getRank()),
                    () -> assertThat(dto.startsAt()).isEqualTo(difficultyLevel.getStartsAt()),
                    () -> assertThat(dto.endsAt()).isEqualTo(difficultyLevel.getEndsAt()),
                    () -> assertThat(dto.isRecurring()).isEqualTo(difficultyLevel.getIsRecurring()),
                    () -> assertThat(dto.createdAt()).isEqualTo(difficultyLevel.getCreatedAt()),
                    () -> assertThat(dto.updatedAt()).isEqualTo(difficultyLevel.getUpdatedAt()),
                    () -> assertThat(dto.disabledAt()).isEqualTo(difficultyLevel.getDisabledAt())
            );
        }

        @ParameterizedTest(name = "createdAt {0} days ago => isNew = {1}")
        @MethodSource("com.dassonville.api.util.TestDataFactory#provideCreatedAtsAndIsNew")
        @DisplayName("PUBLIC - Récupérer tous les niveaux de difficulté actifs")
        void getAllActiveDifficultyLevels(LocalDateTime createdAt, boolean expectedIsNew) {

            PublicDifficultyLevelProjection projection = new TestPublicDifficultyLevelProjection(createdAt);

            // Given
            when(quizRepository.existsByIdAndDisabledAtIsNull(id))
                    .thenReturn(true);
            when(difficultyLevelRepository.findByDisabledAtIsNullOrderByRank(anyLong()))
                    .thenReturn(List.of(projection));

            // When
            List<DifficultyLevelPublicDTO> results = difficultyLevelService.getAllActiveDifficultyLevels(id);
            DifficultyLevelPublicDTO dto = results.getFirst();


            // Then
            assertAll("Verify method calls",
                    () -> verify(quizRepository).existsByIdAndDisabledAtIsNull(id),
                    () -> verify(difficultyLevelRepository).findByDisabledAtIsNullOrderByRank(id)
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(results).isNotEmpty(),
                    () -> assertThat(results).hasSize(1),
                    () -> assertThat(dto.id()).isEqualTo(projection.getId()),
                    () -> assertThat(dto.name()).isEqualTo(projection.getName()),
                    () -> assertThat(dto.label()).isEqualTo(projection.getLabel()),
                    () -> assertThat(dto.description()).isEqualTo(projection.getDescription()),
                    () -> assertThat(dto.isNew()).isEqualTo(expectedIsNew)
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé ou désactivé")
        void getAllActiveDifficultyLevels_quizNotFoundOrDisabled() {
            // Given
            when(quizRepository.existsByIdAndDisabledAtIsNull(id))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> difficultyLevelService.getAllActiveDifficultyLevels(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).existsByIdAndDisabledAtIsNull(id),
                    () -> verifyNoInteractions(difficultyLevelRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Aucun niveau de difficulté actif trouvé")
        void getAllActiveDifficultyLevels_noActiveLevelsFound() {
            // Given
            when(quizRepository.existsByIdAndDisabledAtIsNull(id))
                    .thenReturn(true);
            when(difficultyLevelRepository.findByDisabledAtIsNullOrderByRank(anyLong()))
                    .thenReturn(List.of());

            // When
            MisconfiguredException exception = assertThrows(MisconfiguredException.class, () -> difficultyLevelService.getAllActiveDifficultyLevels(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_MISCONFIGURED),
                    () -> verify(quizRepository).existsByIdAndDisabledAtIsNull(id),
                    () -> verify(difficultyLevelRepository).findByDisabledAtIsNullOrderByRank(id)
            );
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
            assertAll("Verify method calls",
                    () -> verify(difficultyLevelRepository).findById(id)
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.name()).isEqualTo(difficultyLevel.getName()),
                    () -> assertThat(result.isReference()).isEqualTo(difficultyLevel.getIsReference()),
                    () -> assertThat(result.label()).isEqualTo(difficultyLevel.getLabel()),
                    () -> assertThat(result.description()).isEqualTo(difficultyLevel.getDescription()),
                    () -> assertThat(result.rank()).isEqualTo(difficultyLevel.getRank()),
                    () -> assertThat(result.startsAt()).isEqualTo(difficultyLevel.getStartsAt()),
                    () -> assertThat(result.endsAt()).isEqualTo(difficultyLevel.getEndsAt()),
                    () -> assertThat(result.isRecurring()).isEqualTo(difficultyLevel.getIsRecurring()),
                    () -> assertThat(result.createdAt()).isEqualTo(difficultyLevel.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(difficultyLevel.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(difficultyLevel.getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void findById_nonExistingId() {
            // Given
            when(difficultyLevelRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When & Then
            NotFoundException exception = assertThrows(NotFoundException.class, () -> difficultyLevelService.findById(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DIFFICULTY_NOT_FOUND),
                    () -> verify(difficultyLevelRepository).findById(id)
            );
        }
    }
}
