package com.dassonville.api.service;


import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Quiz")
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private ThemeService themeService;

    private QuizService quizService;

    private final QuizMapper quizMapper = Mappers.getMapper(QuizMapper.class);


    private long id;
    private Quiz quiz;
    private QuizUpsertDTO quizUpsertDTO;


    @BeforeEach
    void setUp() {
        quizService = new QuizService(quizRepository, quizMapper, themeService);

        id = 1L;

        quiz = new Quiz();
        quiz.setId(id);
        quiz.setTitle("Test Quiz");
        quiz.setIsVipOnly(true);
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setDisabledAt(LocalDateTime.now());
        quiz.setCategory(new Category(1));
        quiz.setTheme(new Theme(1));

        quizUpsertDTO = new QuizUpsertDTO(
                " test Quiz",
                true,
                1L,
                1L
        );
    }


    @Nested
    @DisplayName("ADMIN - Récupérer un quiz par son ID")
    class FindByIdForAdminTest {

        @Test
        @DisplayName("Succès")
        void findByIdForAdmin_quizFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            QuizAdminDetailsDTO result = quizService.findByIdForAdmin(id);

            // Then
            verify(quizRepository).findById(anyLong());
            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo(quiz.getTitle());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void findByIdForAdmin_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenThrow(NotFoundException.class);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForAdmin(id));

            verify(quizRepository).findById(anyLong());
        }
    }


    @Nested
    @DisplayName("Créer un quiz")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newQuiz() {
            // Given
            when(quizRepository.existsByTitleIgnoreCase(anyString()))
                    .thenReturn(false);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            QuizAdminDetailsDTO result = quizService.create(quizUpsertDTO);

            // Then
            verify(quizRepository).existsByTitleIgnoreCase(anyString());
            verify(quizRepository).save(any(Quiz.class));

            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo(quiz.getTitle());
        }

        @Test
        @DisplayName("Erreur - Titre déjà existant")
        void create_existingTitle() {
            // Given
            when(quizRepository.existsByTitleIgnoreCase(anyString()))
                    .thenReturn(true);

            // When & then
            assertThrows(AlreadyExistException.class, () -> quizService.create(quizUpsertDTO));

            verify(quizRepository).existsByTitleIgnoreCase(anyString());
            verify(quizRepository, never()).save(any(Quiz.class));
        }
    }


    @Nested
    @DisplayName("Mettre à jour un quiz")
    class UpdateTest {

        @Test
        @DisplayName("Succès")
        void update_quiz() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            QuizAdminDetailsDTO result = quizService.update(id, quizUpsertDTO);

            // Then
            verify(quizRepository).existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).save(any(Quiz.class));

            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo(quiz.getTitle());
        }

        @Test
        @DisplayName("Erreur - Titre déjà existant")
        void update_existingTitle() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When & then
            assertThrows(AlreadyExistException.class, () -> quizService.update(id, quizUpsertDTO));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository).existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void update_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenThrow(NotFoundException.class);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.update(id, quizUpsertDTO));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository, never()).existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
        }
    }


    @Nested
    @DisplayName("Supprimer un quiz")
    class DeleteTest {

        @Test
        @DisplayName("Succès - & Theme actif")
        void delete_quizAndThemeActive() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByThemeIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);


            // When
            quizService.delete(id);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).deleteById(anyLong());
            verify(quizRepository).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Succès - & Theme inactif")
        void delete_quizAndThemeInactive() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByThemeIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(0);

            // When
            quizService.delete(id);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).deleteById(anyLong());
            verify(quizRepository).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void delete_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenThrow(NotFoundException.class);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.delete(id));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository, never()).deleteById(anyLong());
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'un quiz")
    class UpdateVisibilityTest {

        @Test
        @DisplayName("Succès - Quiz activé")
        void updateVisibility_quizEnable() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByIdAndQuestionsDisabledAtIsNull(anyLong()))
                    .thenReturn(10);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            quizService.updateVisibility(id, true);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).countByIdAndQuestionsDisabledAtIsNull(anyLong());
            verify(quizRepository).save(any(Quiz.class));
            verify(quizRepository, never()).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Erreur - Impossible d'activer le quiz : pas assez de questions")
        void updateVisibility_quizEnableNotEnoughQuestions() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByIdAndQuestionsDisabledAtIsNull(anyLong()))
                    .thenReturn(0);

            // When & then
            assertThrows(ActionNotAllowedException.class, () -> quizService.updateVisibility(id, true));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository).countByIdAndQuestionsDisabledAtIsNull(anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
            verify(quizRepository, never()).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Succès - Quiz désactivé & thème actif")
        void updateVisibility_quizDisable() {
            // Given
            quiz.setDisabledAt(null);
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByThemeIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            quizService.updateVisibility(id, false);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).save(any(Quiz.class));
            verify(quizRepository).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService, never()).updateVisibility(anyLong(), anyBoolean());
        }
        
        @Test
        @DisplayName("Succès - Quiz désactivé & thème inactif")
        void updateVisibility_quizDisableAndThemeInactive() {
            // Given
            quiz.setDisabledAt(null);
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByThemeIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(0);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            quizService.updateVisibility(id, false);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository).save(any(Quiz.class));
            verify(quizRepository).countByThemeIdAndDisabledAtIsNull(anyLong());
            verify(themeService).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("RAS - Quiz déjà inactif")
        void updateVisibility_quizAlreadyInactive() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            quizService.updateVisibility(id, false);

            // Then
            verify(quizRepository).findById(anyLong());
            verify(quizRepository, never()).countByIdAndQuestionsDisabledAtIsNull(anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void updateVisibility_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenThrow(NotFoundException.class);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.updateVisibility(id, false));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
        }
    }
}
