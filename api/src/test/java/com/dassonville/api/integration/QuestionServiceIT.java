package com.dassonville.api.integration;


import com.dassonville.api.constant.RequestActionType;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionInsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionUpdateDTO;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.ClassicAnswerRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.service.QuestionService;
import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("IT - Service : Question")
public class QuestionServiceIT {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ClassicAnswerRepository classicAnswerRepository;


    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Nested
    @DisplayName("Création de question")
    class CreatingQuestion {

        @Test
        @Transactional
        @DisplayName("Succès")
        public void shouldCreateQuestion() {
            // Given
            long quizId = 1L;
            ClassicQuestionInsertDTO classicQuestionInsertDTO = new ClassicQuestionInsertDTO(
                    RequestActionType.CLASSIC_INSERT,
                    " question",
                    "Explication de la réponse",
                    List.of(
                            new ClassicAnswerUpsertDTO(" paris", true),
                            new ClassicAnswerUpsertDTO(" londres", false),
                            new ClassicAnswerUpsertDTO(" berlin", false),
                            new ClassicAnswerUpsertDTO(" madrid", false)
                    )
            );

            // When
            QuestionAdminDTO createdQuestion = questionService.create(quizId, classicQuestionInsertDTO);

            // Then
            Question question = questionRepository.findById(createdQuestion.id()).get();
            assertThat(question).isNotNull();
            assertThat(question.getText()).isEqualTo("Question");
            assertThat(question.getClassicAnswers().size()).isEqualTo(4);
            assertThat(question.getClassicAnswers().getFirst()).isNotNull();
            assertThat(question.getClassicAnswers().getFirst().getText()).isEqualTo("Paris");
            assertThat(question.getClassicAnswers().getFirst().getIsCorrect()).isTrue();
            assertThat(question.getQuizzes().getFirst().getId()).isEqualTo(quizId);
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void shouldFailToCreate_WhenQuizNotFound() {
            // Given
            long quizId = 9999L;
            ClassicQuestionInsertDTO classicQuestionInsertDTO = new ClassicQuestionInsertDTO(
                    RequestActionType.CLASSIC_INSERT,
                    " question",
                    "Explication de la réponse",
                    List.of(
                            new ClassicAnswerUpsertDTO(" paris", true),
                            new ClassicAnswerUpsertDTO(" londres", false),
                            new ClassicAnswerUpsertDTO(" berlin", false),
                            new ClassicAnswerUpsertDTO(" madrid", false)
                    )
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.create(quizId, classicQuestionInsertDTO));
        }

        @Test
        @DisplayName("Erreur - Question déjà existante pour le quiz")
        public void shouldFailToCreate_WhenQuestionAlreadyExists() {
            // Given
            long quizId = 1L;

            ClassicQuestionInsertDTO classicQuestionInsertDTO = new ClassicQuestionInsertDTO(
                    RequestActionType.CLASSIC_INSERT,
                    " qui est le fondateur du stoïcisme ?",
                    "Explication de la réponse",
                    List.of(
                            new ClassicAnswerUpsertDTO(" paris", true),
                            new ClassicAnswerUpsertDTO(" londres", false),
                            new ClassicAnswerUpsertDTO(" berlin", false),
                            new ClassicAnswerUpsertDTO(" madrid", false)
                    )
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> questionService.create(quizId, classicQuestionInsertDTO));
        }
    }


    @Nested
    @DisplayName("Mise à jour de question")
    class UpdatingQuestion {

        @Test
        @Transactional
        @DisplayName("Succès")
        public void shouldUpdateQuestion() {
            // Given
            long questionId = 1L;
            ClassicQuestionUpdateDTO questionInsertDTO = new ClassicQuestionUpdateDTO(
                    RequestActionType.CLASSIC_UPDATE,
                    " question",
                    "Explication de la réponse"
            );

            // When
            QuestionAdminDTO updatedQuestion = questionService.update(questionId, questionInsertDTO);

            // Then
            Question question = questionRepository.findById(updatedQuestion.id()).get();
            assertThat(question).isNotNull();
            assertThat(question.getText()).isEqualTo("Question");
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        public void shouldFailToUpdate_WhenQuestionNotFound() {
            // Given
            long questionId = 9999L;
            ClassicQuestionUpdateDTO questionInsertDTO = new ClassicQuestionUpdateDTO(
                    RequestActionType.CLASSIC_UPDATE,
                    " question",
                    "Explication de la réponse"
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.update(questionId, questionInsertDTO));
        }

        @Test
        @Transactional
        @DisplayName("Erreur - Question déjà existante pour le quiz")
        public void shouldFailToUpdate_WhenQuestionAlreadyExists() {
            // Given
            long questionId = 1L;
            ClassicQuestionUpdateDTO questionInsertDTO = new ClassicQuestionUpdateDTO(
                    RequestActionType.CLASSIC_UPDATE,
                    " qui est le fondateur du stoïcisme ?",
                    "Explication de la réponse"
            );

            // When / Then
            assertThrows(AlreadyExistException.class, () -> questionService.update(questionId, questionInsertDTO));
        }
    }


    @Nested
    @DisplayName("Suppression de question")
    class DeletingQuestion {

        @Test
        @DisplayName("Succès - Quiz actif")
        public void shouldDeleteQuestion() {
            // Given
            long questionId = 1L;

            // When
            questionService.delete(questionId);

            // Then
            assertThat(questionRepository.existsById(questionId)).isFalse();
            assertThat(classicAnswerRepository.existsByQuestionId(questionId)).isFalse();

            Quiz quiz = quizRepository.findById(1L).get();
            assertThat(quiz.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Succès - Quiz désactivé")
        public void shouldDeleteQuestionAndDisableQuiz() {

            // Given
            long questionId = 291L;

            // When
            questionService.delete(questionId);

            // Then
            Quiz quiz = quizRepository.findById(16L).get();
            Quiz quiz2 = quizRepository.findById(19L).get();
            assertThat(quiz.getDisabledAt()).isNotNull();
            assertThat(quiz2.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        public void shouldFailToDelete_WhenQuestionNotFound() {
            // Given
            long questionId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.delete(questionId));
        }
    }


    @Nested
    @DisplayName("Désactivation / activation de question")
    class DisablingQuestion {

        @Test
        @DisplayName("Succès - Question désactivée & Quiz actif")
        public void shouldDisableQuestion() {
            // Given
            long questionId = 1L;

            // When
            questionService.updateVisibility(questionId, false);

            // Then
            Question question = questionRepository.findById(questionId).get();
            assertThat(question.getDisabledAt()).isNotNull();

            Quiz quiz = quizRepository.findById(1L).get();
            assertThat(quiz.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Succès - Question désactivée & Quiz + Thème désactivé")
        public void shouldDisableQuestionAndDisableQuiz() {

            // Given
            long questionId = 300L;

            // When
            questionService.updateVisibility(questionId, false);

            // Then
            Quiz quiz = quizRepository.findById(17L).get();
            Quiz quiz2 = quizRepository.findById(19L).get();
            Theme theme = themeRepository.findById(2L).get();
            assertThat(quiz.getDisabledAt()).isNotNull();
            assertThat(quiz2.getDisabledAt()).isNotNull();
            assertThat(theme.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Question activée")
        public void shouldEnableQuestion() {
            // Given
            long questionId = 290L;

            // When
            questionService.updateVisibility(questionId, true);

            // Then
            Question question = questionRepository.findById(questionId).get();
            assertThat(question.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        public void shouldFailToDisable_WhenQuestionNotFound() {
            // Given
            long questionId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.updateVisibility(questionId, false));
        }
    }
}
