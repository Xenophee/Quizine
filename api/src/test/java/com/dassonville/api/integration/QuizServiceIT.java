package com.dassonville.api.integration;


import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.GameType;
import com.dassonville.api.constant.Type;
import com.dassonville.api.dto.request.QuizUpsertDTO;
import com.dassonville.api.dto.response.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    @DisplayName("PUBLIC - Récupérer les quiz par thèmes")
    class GettingQuizzesByThemeForPublic {

        @Test
        @DisplayName("Retourne tous les quiz si la liste des thèmes est vide")
        public void shouldFindAllQuizzesByThemeIdForPublic_WhenExistingTheme() {
            // Given
            List<Long> idToGet = List.of();
            Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());

            // When
            Page<QuizPublicDTO> quizzes = quizService.findAllByThemeIdsForUser(idToGet, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(3);
            assertThat(quizzes.getContent()).hasSize(5);

            assertThat(quizzes.getContent().getFirst().id()).isEqualTo(16L);
            assertThat(quizzes.getContent().getFirst().title()).isEqualTo("La langage Java : Concepts et Pratiques");
            assertThat(quizzes.getContent().getFirst().quizType()).isEqualTo("Classique");
            assertThat(quizzes.getContent().getFirst().masteryLevel()).isEqualTo("AVERTI");
            assertThat(quizzes.getContent().getFirst().isNew()).isTrue();
            assertThat(quizzes.getContent().getFirst().numberOfQuestions()).isEqualTo((byte) 10);
            assertThat(quizzes.getContent().getFirst().category()).isNull();
            assertThat(quizzes.getContent().getFirst().theme()).isEqualTo("Informatique");
        }

        @Test
        @DisplayName("Retourne une page vide si aucun thème valide n'est trouvé")
        public void shouldFindAllQuizzesByThemeIdForPublic_WhenExistingThemeAndVisibleTrue() {
            // Given
            List<Long> idToGet = List.of(9999L);
            Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());

            // When
            Page<QuizPublicDTO> quizzes = quizService.findAllByThemeIdsForUser(idToGet, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(0);
            assertThat(quizzes.getContent()).isEmpty();
        }

        @Test
        @DisplayName("Retourne les quiz associés aux thèmes valides")
        public void shouldFindAllQuizzesByThemeIdForPublic_WhenExistingThemeAndVisibleFalse() {
            // Given
            List<Long> idToGet = List.of(3L, 4L);
            Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());

            // When
            Page<QuizPublicDTO> quizzes = quizService.findAllByThemeIdsForUser(idToGet, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(1);
            assertThat(quizzes.getContent()).hasSize(3);

            assertThat(quizzes.getContent().getFirst().id()).isEqualTo(11L);
            assertThat(quizzes.getContent().getFirst().title()).isEqualTo("Philosophie française : figures, courants et concepts majeurs");
            assertThat(quizzes.getContent().getFirst().isNew()).isTrue();
            assertThat(quizzes.getContent().getFirst().numberOfQuestions()).isEqualTo((byte) 20);
            assertThat(quizzes.getContent().getFirst().category()).isEqualTo("Philosophie");
            assertThat(quizzes.getContent().getFirst().theme()).isEqualTo("Sciences humaines");
        }
    }


    @Nested
    @DisplayName("ADMIN - Récupérer les quiz par thème")
    class GettingQuizzesByThemeForAdmin {

        @Test
        @DisplayName("Succès - visible = null")
        public void shouldFindAllQuizzesByThemeIdForAdmin_WhenExistingTheme() {
            // Given
            long idToGet = 4L;
            Pageable pageable = PageRequest.of(0, 5, Sort.by("title"));

            // When
            Page<QuizAdminDTO> quizzes = quizService.findAllByThemeIdForAdmin(idToGet, null, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(1);
            assertThat(quizzes.getContent()).hasSize(5);

            assertThat(quizzes.getContent().getFirst().id()).isEqualTo(8L);
            assertThat(quizzes.getContent().getFirst().title()).isEqualTo("Mythologie égyptienne : dieux, légendes et symboles");
            assertThat(quizzes.getContent().getFirst().numberOfQuestions()).isEqualTo((byte) 20);
            assertThat(quizzes.getContent().getFirst().hasEnoughQuestionsForActivation()).isTrue();
            assertThat(quizzes.getContent().getFirst().createdAt()).isNotNull();
            assertThat(quizzes.getContent().getFirst().disabledAt()).isNotNull();
            assertThat(quizzes.getContent().getFirst().category()).isEqualTo("Mythologie & Religion");
        }

        @Test
        @DisplayName("Succès - visible = true")
        public void shouldFindAllQuizzesByThemeIdForAdmin_WhenExistingThemeAndVisibleTrue() {
            // Given
            long idToGet = 4L;
            Pageable pageable = PageRequest.of(0, 5, Sort.by("title"));

            // When
            Page<QuizAdminDTO> quizzes = quizService.findAllByThemeIdForAdmin(idToGet, true, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(1);
            assertThat(quizzes.getContent()).hasSize(4);

            assertThat(quizzes.getContent().getFirst().id()).isEqualTo(9L);
            assertThat(quizzes.getContent().getFirst().title()).isEqualTo("Mythologie nordique : dieux, créatures et légendes vikings");
            assertThat(quizzes.getContent().getFirst().numberOfQuestions()).isEqualTo((byte) 20);
            assertThat(quizzes.getContent().getFirst().hasEnoughQuestionsForActivation()).isTrue();
            assertThat(quizzes.getContent().getFirst().createdAt()).isNotNull();
            assertThat(quizzes.getContent().getFirst().disabledAt()).isNull();
            assertThat(quizzes.getContent().getFirst().category()).isEqualTo("Mythologie & Religion");
        }

        @Test
        @DisplayName("Succès - visible = false")
        public void shouldFindAllQuizzesByThemeIdForAdmin_WhenExistingThemeAndVisibleFalse() {
            // Given
            long idToGet = 4L;
            Pageable pageable = PageRequest.of(0, 5, Sort.by("title"));

            // When
            Page<QuizAdminDTO> quizzes = quizService.findAllByThemeIdForAdmin(idToGet, false, pageable);

            // Then
            assertThat(quizzes).isNotNull();
            assertThat(quizzes.getTotalPages()).isEqualTo(1);
            assertThat(quizzes.getContent()).hasSize(1);

            assertThat(quizzes.getContent().getFirst().id()).isEqualTo(8L);
            assertThat(quizzes.getContent().getFirst().title()).isEqualTo("Mythologie égyptienne : dieux, légendes et symboles");
            assertThat(quizzes.getContent().getFirst().numberOfQuestions()).isEqualTo((byte) 20);
            assertThat(quizzes.getContent().getFirst().hasEnoughQuestionsForActivation()).isTrue();
            assertThat(quizzes.getContent().getFirst().createdAt()).isNotNull();
            assertThat(quizzes.getContent().getFirst().disabledAt()).isNotNull();
            assertThat(quizzes.getContent().getFirst().category()).isEqualTo("Mythologie & Religion");
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void shouldFailToFindAllQuizzesByThemeIdForAdmin_WhenNonExistingTheme() {
            // Given
            long idToGet = 9999L;
            Pageable pageable = PageRequest.of(0, 5);

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findAllByThemeIdForAdmin(idToGet, null, pageable));
        }
    }

    @Nested
    @DisplayName("Récupération de quiz par ID")
    class GettingQuiz {

        @Test
        @DisplayName("ADMIN - Succès")
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
            assertThat(quiz.categoryId()).isEqualTo(7L);
            assertThat(quiz.themeId()).isEqualTo(4L);
        }

        @Test
        @DisplayName("ADMIN - Erreur - Quiz non trouvé")
        public void shouldFailToGetQuizAdminById_WhenNonExistingQuiz() {
            // Given
            long idToGet = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForAdmin(idToGet));
        }

        @Test
        @DisplayName("PUBLIC - Succès")
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
        @DisplayName("PUBLIC - Erreur - Quiz non trouvé")
        public void shouldFailToGetQuizPublicById_WhenNonExistingQuiz() {
            // Given
            long idToGet = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> quizService.findByIdForUser(idToGet));
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
                    Type.CLASSIC,
                    " test Quiz",
                    "Description du test Quiz",
                    1L,
                    1L,
                    5L
            );

            // When
            QuizAdminDetailsDTO createdQuiz = quizService.create(quizUpsertDTO);

            // Then
            Quiz quiz = quizRepository.findById(createdQuiz.id()).get();
            assertThat(quiz).isNotNull();
            assertThat(quiz.getTitle()).isEqualTo("Test Quiz");
            assertThat(quiz.getCreatedAt()).isNotNull();
            assertThat(quiz.getDisabledAt()).isNotNull();
            assertThat(quiz.getCategory().getId()).isEqualTo(5L);
            assertThat(quiz.getTheme().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Titre de quiz déjà existant")
        public void shouldFailToCreate_WhenExistingQuiz() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    Type.CLASSIC,
                    " philosophie grecque : penseurs, écoles et concepts fondateurs",
                    "Description du test Quiz",
                    9L,
                    1L,
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
                    Type.CLASSIC,
                    " test Quiz",
                    "Description du test Quiz",
                    2L,
                    3L,
                    4L
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
            assertThat(quiz.getCategory().getId()).isEqualTo(4L);
            assertThat(quiz.getTheme().getId()).isEqualTo(3L);
        }

        @Test
        @DisplayName("Erreur - Titre de quiz déjà existant")
        public void shouldFailToUpdate_WhenQuizWithExistingTitle() {
            // Given
            QuizUpsertDTO quizUpsertDTO = new QuizUpsertDTO(
                    Type.CLASSIC,
                    " littérature gothique : motifs, personnages et influences",
                    "Description du test Quiz",
                    9L,
                    1L,
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
                    Type.CLASSIC,
                    "test Quiz",
                    "Description du test Quiz",
                    9L,
                    1L,
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
            assertThat(questionRepository.existsByQuizzesId(idToDelete)).isFalse();
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
            long idToEnable = 8L;

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
            long idToEnable = 18L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> quizService.updateVisibility(idToEnable, true));
        }
    }
}
