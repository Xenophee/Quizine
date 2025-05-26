package com.dassonville.api.service;


import com.dassonville.api.dto.*;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.QuestionForPlayProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Mock
    private ThemeRepository themeRepository;
    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;
    @Mock
    private QuestionRepository questionRepository;

    private QuizService quizService;

    private final QuizMapper quizMapper = Mappers.getMapper(QuizMapper.class);


    private long id;
    private Quiz quiz;
    private QuizUpsertDTO quizUpsertDTO;


    @BeforeEach
    void setUp() {
        quizService = new QuizService(quizRepository, quizMapper, themeService, difficultyLevelRepository, questionRepository, themeRepository);

        id = 1L;

        quiz = new Quiz();
        quiz.setId(id);
        quiz.setTitle("Test Quiz");
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setDisabledAt(LocalDateTime.now());
        quiz.setCategory(new Category(1));
        quiz.setTheme(new Theme(1));

        quizUpsertDTO = new QuizUpsertDTO(
                " test Quiz",
                1L,
                1L
        );
    }


    @Nested
    @DisplayName("ADMIN - Récupérer les quiz par thème")
    class GetQuizzesByThemeTest {

        @BeforeEach
        void setUp() {
            quiz.setId(id);
            quiz.setTitle("Test Quiz");
            quiz.setCreatedAt(LocalDateTime.now());
            quiz.setDisabledAt(LocalDateTime.now());
            quiz.setCategory(new Category(1));
            quiz.setTheme(new Theme(1));
        }

        @Test
        @DisplayName("Succès - visible = null")
        void shouldReturnAllQuizzes_WhenVisibleIsNull() {
            // Given
            Long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(new Quiz(), new Quiz());
            Page<Quiz> quizPage = new PageImpl<>(quizzes);
            when(themeRepository.existsById(themeId)).thenReturn(true);
            when(quizRepository.findByThemeId(themeId, pageable)).thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, null, pageable);

            // Then
            verify(themeRepository).existsById(themeId);
            verify(quizRepository).findByThemeId(themeId, pageable);
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNull(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNotNull(anyLong(), any(Pageable.class));

            assertThat(result).isNotNull();
            assertThat(result.getContent().size()).isEqualTo(2);
        }

        @Test
        @DisplayName("Succès - visible = true")
        void shouldReturnVisibleQuizzes_WhenVisibleIsTrue() {
            // Given
            Long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(new Quiz());
            Page<Quiz> quizPage = new PageImpl<>(quizzes);
            when(themeRepository.existsById(themeId)).thenReturn(true);
            when(quizRepository.findByThemeIdAndDisabledAtIsNull(themeId, pageable)).thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, true, pageable);

            // Then
            verify(themeRepository).existsById(themeId);
            verify(quizRepository).findByThemeIdAndDisabledAtIsNull(themeId, pageable);
            verify(quizRepository, never()).findByThemeId(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNotNull(anyLong(), any(Pageable.class));

            assertThat(result).isNotNull();
            assertThat(result.getContent().size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Succès - visible = false")
        void shouldReturnDisabledQuizzes_WhenVisibleIsFalse() {
            // Given
            Long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(new Quiz());
            Page<Quiz> quizPage = new PageImpl<>(quizzes);
            when(themeRepository.existsById(themeId)).thenReturn(true);
            when(quizRepository.findByThemeIdAndDisabledAtIsNotNull(themeId, pageable)).thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, false, pageable);

            // Then
            verify(themeRepository).existsById(themeId);
            verify(quizRepository).findByThemeIdAndDisabledAtIsNotNull(themeId, pageable);
            verify(quizRepository, never()).findByThemeId(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNull(anyLong(), any(Pageable.class));


            assertThat(result).isNotNull();
            assertThat(result.getContent().size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void shouldThrowNotFoundException_WhenThemeNotFound() {
            // Given
            Long themeId = 9999L;
            Pageable pageable = PageRequest.of(0, 10);
            when(themeRepository.existsById(themeId)).thenReturn(false);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.findAllByThemeIdForAdmin(themeId, null, pageable));

            verify(themeRepository).existsById(themeId);
            verify(quizRepository, never()).findByThemeId(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNull(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNotNull(anyLong(), any(Pageable.class));
        }
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
    @DisplayName("PUBLIC - Récupérer un quiz par son ID")
    class FindByIdForUserTest {

        @Test
        @DisplayName("Succès")
        void findByIdForUser_quizFound() {
            // Given
            when(quizRepository.findByIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            QuizPublicDetailsDTO result = quizService.findByIdForUser(id);

            // Then
            verify(quizRepository).findByIdAndDisabledAtIsNull(anyLong());

            assertThat(result).isNotNull();
            assertThat(result.title()).isEqualTo(quiz.getTitle());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void findByIdForUser_quizNotFound() {
            // Given
            when(quizRepository.findByIdAndDisabledAtIsNull(anyLong()))
                    .thenThrow(NotFoundException.class);

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForUser(id));

            verify(quizRepository).findByIdAndDisabledAtIsNull(anyLong());
        }
    }


    @Nested
    @DisplayName("Récupérer les questions d'un quiz pour jouer")
    class FindAllQuestionsByQuizIdForPlayTest {

        @Test
        @DisplayName("Succès")
        void shouldReturnQuestions_WhenQuizAndDifficultyExist() {
            // Given
            long quizId = 1L;
            long difficultyLevelId = 1L;
            byte maxAnswers = 3;

            DifficultyLevel difficultyLevel = new DifficultyLevel();
            difficultyLevel.setMaxAnswers(maxAnswers);

            QuestionForPlayProjection.AnswersForPlay answer1 = mock(QuestionForPlayProjection.AnswersForPlay.class);
            when(answer1.getId()).thenReturn(1L);
            when(answer1.getText()).thenReturn("Réponse 1");
            when(answer1.getIsCorrect()).thenReturn(true);

            QuestionForPlayProjection.AnswersForPlay answer2 = mock(QuestionForPlayProjection.AnswersForPlay.class);
            when(answer2.getId()).thenReturn(2L);
            when(answer2.getText()).thenReturn("Réponse 2");
            when(answer2.getIsCorrect()).thenReturn(false);

            QuestionForPlayProjection question1 = mock(QuestionForPlayProjection.class);
            when(question1.getId()).thenReturn(1L);
            when(question1.getText()).thenReturn("Question 1");
            when(question1.getAnswers()).thenReturn(List.of(answer1, answer2));

            QuestionForPlayProjection question2 = mock(QuestionForPlayProjection.class);
            when(question2.getId()).thenReturn(2L);
            when(question2.getText()).thenReturn("Question 2");
            when(question2.getAnswers()).thenReturn(List.of(answer1));

            List<QuestionForPlayProjection> questions = new ArrayList<>(List.of(question1, question2));

            when(quizRepository.existsByIdAndDisabledAtIsNull(quizId))
                    .thenReturn(true);
            when(difficultyLevelRepository.findByIdAndDisabledAtIsNull(difficultyLevelId))
                    .thenReturn(Optional.of(difficultyLevel));
            when(questionRepository.findByQuizIdAndDisabledAtIsNullAndAnswersDisabledAtIsNull(quizId))
                    .thenReturn(questions);

            // When
            List<QuestionForPlayDTO> result = quizService.findAllQuestionsByQuizIdForPlay(quizId, difficultyLevelId);

            // Then
            verify(quizRepository).existsByIdAndDisabledAtIsNull(quizId);
            verify(difficultyLevelRepository).findByIdAndDisabledAtIsNull(difficultyLevelId);
            verify(questionRepository).findByQuizIdAndDisabledAtIsNullAndAnswersDisabledAtIsNull(quizId);

            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(questions.size());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void shouldThrowNotFoundException_WhenQuizDoesNotExist() {
            // Given
            long quizId = 1L;
            long difficultyLevelId = 1L;

            when(quizRepository.existsByIdAndDisabledAtIsNull(quizId))
                    .thenReturn(false);

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findAllQuestionsByQuizIdForPlay(quizId, difficultyLevelId));

            verify(quizRepository).existsByIdAndDisabledAtIsNull(quizId);
            verifyNoInteractions(difficultyLevelRepository, questionRepository);
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        void shouldThrowNotFoundException_WhenDifficultyLevelDoesNotExist() {
            // Given
            long quizId = 1L;
            long difficultyLevelId = 1L;

            when(quizRepository.existsByIdAndDisabledAtIsNull(quizId))
                    .thenReturn(true);
            when(difficultyLevelRepository.findByIdAndDisabledAtIsNull(difficultyLevelId))
                    .thenReturn(Optional.empty());

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findAllQuestionsByQuizIdForPlay(quizId, difficultyLevelId));

            verify(quizRepository).existsByIdAndDisabledAtIsNull(quizId);
            verify(difficultyLevelRepository).findByIdAndDisabledAtIsNull(difficultyLevelId);
            verifyNoInteractions(questionRepository);
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
                    .thenReturn(Optional.empty());

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
                    .thenReturn(Optional.empty());

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
                    .thenReturn(Optional.empty());

            // When & then
            assertThrows(NotFoundException.class, () -> quizService.updateVisibility(id, false));

            verify(quizRepository).findById(anyLong());
            verify(quizRepository, never()).save(any(Quiz.class));
        }
    }
}
