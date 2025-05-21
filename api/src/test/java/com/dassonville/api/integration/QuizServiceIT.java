package com.dassonville.api.integration;


import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.service.QuizService;
import jakarta.transaction.Transactional;
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
@DisplayName("IT - Service : Quiz")
public class QuizServiceIT {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ThemeRepository themeRepository;


    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Nested
    @DisplayName("Récupération de quiz")
    class GettingQuiz {


        @Test
        @DisplayName("ADMIN - Succès - Récupération d'un quiz par ID")
        public void shouldGetQuizAdminById_WhenExistingQuiz() {
            // Given
            long idToGet = 1L;

            // When
            QuizAdminDetailsDTO quiz = quizService.findByIdForAdmin(idToGet);

            // Then
            assertThat(quiz).isNotNull();
            assertThat(quiz.id()).isEqualTo(idToGet);
            assertThat(quiz.title()).isEqualTo("Philosophie grecque : penseurs, écoles et concepts fondateurs");
            assertThat(quiz.isVipOnly()).isTrue();
            assertThat(quiz.createdAt()).isNotNull();
            assertThat(quiz.disabledAt()).isNull();
            assertThat(quiz.categoryId()).isEqualTo(9L);
            assertThat(quiz.themeId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("ADMIN - Erreur - Quiz non trouvé par ID")
        public void shouldFailToGetQuizAdminById_WhenNonExistingQuiz() {
            // Given
            long idToGet = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForAdmin(idToGet));
        }
    }


    @Nested
    @DisplayName("Création de quiz")
    class CreatingQuiz {

        @Test
        @DisplayName("Succès")
        public void shouldCreateQuiz() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    " test Quiz",
                    false,
                    9L,
                    1L
            );

            // When
            QuizAdminDetailsDTO createdQuiz = quizService.create(quizUpsertDTO);

            // Then
            Quiz quiz = quizRepository.findById(createdQuiz.id()).get();
            assertThat(quiz).isNotNull();
            assertThat(quiz.getTitle()).isEqualTo("Test Quiz");
            assertThat(quiz.getIsVipOnly()).isFalse();
            assertThat(quiz.getCreatedAt()).isNotNull();
            assertThat(quiz.getDisabledAt()).isNotNull();
            assertThat(quiz.getCategory().getId()).isEqualTo(9L);
            assertThat(quiz.getTheme().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Titre de quiz déjà existant")
        public void shouldFailToCreate_WhenExistingQuiz() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    " philosophie grecque : penseurs, écoles et concepts fondateurs",
                    false,
                    9L,
                    1L
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> quizService.create(quizUpsertDTO));
        }
    }


    @Nested
    @DisplayName("Mise à jour de quiz")
    class UpdatingQuiz {

        @Test
        @DisplayName("Succès")
        public void shouldUpdateQuiz_WhenExistingQuiz() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    " test Quiz",
                    true,
                    9L,
                    1L
            );

            // When
            QuizAdminDetailsDTO updatedQuiz = quizService.update(1L, quizUpsertDTO);

            // Then
            Quiz quiz = quizRepository.findById(updatedQuiz.id()).get();
            assertThat(quiz).isNotNull();
            assertThat(quiz.getTitle()).isEqualTo("Test Quiz");
            assertThat(quiz.getIsVipOnly()).isTrue();
            assertThat(quiz.getCreatedAt()).isNotNull();
            assertThat(quiz.getUpdatedAt()).isNotNull();
            assertThat(quiz.getDisabledAt()).isNull();
            assertThat(quiz.getCategory().getId()).isEqualTo(9L);
            assertThat(quiz.getTheme().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Titre de quiz déjà existant")
        public void shouldFailToUpdate_WhenQuizWithExistingTitle() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    " littérature gothique : motifs, personnages et influences",
                    false,
                    9L,
                    1L
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> quizService.update(1L, quizUpsertDTO));
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void shouldFailToUpdate_WhenNonExistingQuiz() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    "test Quiz",
                    false,
                    9L,
                    1L
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.update(9999L, quizUpsertDTO));
        }
    }


    @Nested
    @DisplayName("Suppression de quiz")
    class DeletingQuiz {

        @Test
        @DisplayName("Succès")
        public void shouldDeleteQuiz_WhenExistingQuiz() {
            // Given
            long idToDelete = 1L;

            // When
            quizService.delete(idToDelete);

            // Then
            assertThat(quizRepository.existsById(idToDelete)).isFalse();
            assertThat(questionRepository.existsByQuizId(idToDelete)).isFalse();
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void shouldFailToDeleteQuiz_WhenNonExistingQuiz() {
            // Given
            long idToDelete = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.delete(idToDelete));
        }
    }


    @Nested
    @DisplayName("Désactivation / activation de quiz")
    class DisablingQuiz {

        @Test
        @Transactional
        @DisplayName("Succès - Quiz désactivé & theme actif")
        public void shouldDisableQuiz_WhenExistingQuiz() {
            // Given
            long idToDisable = 1L;

            // When
            quizService.updateVisibility(idToDisable, false);

            // Then
            Quiz quiz = quizRepository.findById(idToDisable).get();
            assertThat(quiz.getDisabledAt()).isNotNull();
            assertThat(quiz.getTheme().getDisabledAt()).isNull();
        }
        
        @Test
        @DisplayName("Succès - Quiz désactivé & theme désactivé")
        public void shouldDisableQuiz_WhenExistingQuizAndThemeInactive() {
            // Given
            long themeId = 1L;
            List<Quiz> activeQuizzesForOneTheme = quizRepository.findByThemeIdAndDisabledAtIsNull(themeId);

            // When
            for (int index = 0; index < activeQuizzesForOneTheme.size(); index++) {
                quizService.updateVisibility(activeQuizzesForOneTheme.get(index).getId(), false);
            }

            // Then
            Theme theme = themeRepository.findById(themeId).get();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Quiz activé, suffisamment de questions")
        public void shouldEnableQuiz_WhenExistingQuizAndEnoughQuestions() {
            // Given
            long idToEnable = 2L;

            // When
            quizService.updateVisibility(idToEnable, true);

            // Then
            Quiz quiz = quizRepository.findById(idToEnable).get();
            assertThat(quiz.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Pas assez de questions, reste inactif")
        public void shouldFailToEnableQuiz_WhenNotEnoughQuestions() {
            // Given
            long idToEnable = 12L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> quizService.updateVisibility(idToEnable, true));
        }
    }
}
