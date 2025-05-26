package com.dassonville.api.integration;


import com.dassonville.api.dto.QuestionForPlayDTO;
import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizPublicDetailsDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.QuestionForPlayProjection;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

        @Test
        @DisplayName("PUBLIC - Succès - Récupération d'un quiz par ID")
        public void shouldGetQuizPublicById_WhenExistingQuiz() {
            // Given
            long idToGet = 1L;

            // When
            QuizPublicDetailsDTO quiz = quizService.findByIdForUser(idToGet);

            // Then
            assertThat(quiz).isNotNull();
            assertThat(quiz.id()).isEqualTo(idToGet);
            assertThat(quiz.title()).isEqualTo("Philosophie grecque : penseurs, écoles et concepts fondateurs");
            assertThat(quiz.isNew()).isTrue();
            assertThat(quiz.numberOfQuestions()).isEqualTo(20);
            assertThat(quiz.category()).isEqualTo("Philosophie");
            assertThat(quiz.theme()).isEqualTo("Sciences humaines");
        }

        @Test
        @DisplayName("PUBLIC - Erreur - Quiz non trouvé par ID")
        public void shouldFailToGetQuizPublicById_WhenNonExistingQuiz() {
            // Given
            long idToGet = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForUser(idToGet));
        }
    }


    @Nested
    @DisplayName("Récupération des questions d'un quiz pour le jeu")
    class GettingQuestionsForPlay {


        @ParameterizedTest
        @ValueSource(longs = {2L, 5L})
        @DisplayName("Succès - Récupération des questions d'un quiz pour le jeu")
        public void shouldFindAllQuestionsByQuizIdForPlay_WhenExistingQuiz(long idToGetDifficulty) {
            // Given
            long idToGet = 11L;

            // When
            List<QuestionForPlayDTO> questions = quizService.findAllQuestionsByQuizIdForPlay(idToGet, idToGetDifficulty);

            // Then
            assertThat(questions).isNotNull();
            assertThat(questions.size()).isEqualTo(21);

            // Vérification de l'ordre des IDs
            List<Long> originalOrder = questionRepository.findByQuizIdAndDisabledAtIsNullAndAnswersDisabledAtIsNull(idToGet)
                    .stream()
                    .map(QuestionForPlayProjection::getId)
                    .toList();
            List<Long> shuffledOrder = questions.stream()
                    .map(QuestionForPlayDTO::id)
                    .toList();

            assertThat(shuffledOrder).isNotEqualTo(originalOrder); // Vérifie que l'ordre a changé
            assertThat(shuffledOrder).containsExactlyInAnyOrderElementsOf(originalOrder); // Vérifie que tous les éléments sont présents
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void shouldFailToFindAllQuestionsByQuizIdForPlay_WhenNonExistingQuiz() {
            // Given
            long idToGetQuiz = 9999L;
            long idToGetDifficulty = 1L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findAllQuestionsByQuizIdForPlay(idToGetQuiz, idToGetDifficulty));
        }

        @Test
        @DisplayName("Erreur - Difficulté non trouvée")
        public void shouldFailToFindAllQuestionsByQuizIdForPlay_WhenNonExistingDifficulty() {
            // Given
            long idToGetQuiz = 1L;
            long idToGetDifficulty = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findAllQuestionsByQuizIdForPlay(idToGetQuiz, idToGetDifficulty));
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
                    9L,
                    1L
            );

            // When
            QuizAdminDetailsDTO createdQuiz = quizService.create(quizUpsertDTO);

            // Then
            Quiz quiz = quizRepository.findById(createdQuiz.id()).get();
            assertThat(quiz).isNotNull();
            assertThat(quiz.getTitle()).isEqualTo("Test Quiz");
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
                    9L,
                    1L
            );

            // When
            QuizAdminDetailsDTO updatedQuiz = quizService.update(1L, quizUpsertDTO);

            // Then
            Quiz quiz = quizRepository.findById(updatedQuiz.id()).get();
            assertThat(quiz).isNotNull();
            assertThat(quiz.getTitle()).isEqualTo("Test Quiz");
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
