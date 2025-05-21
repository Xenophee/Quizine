package com.dassonville.api.integration;


import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
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

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private AnswerRepository answerRepository;


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
            QuestionInsertDTO questionInsertDTO = new QuestionInsertDTO(" question", List.of(
                    new AnswerUpsertDTO(" paris", true),
                    new AnswerUpsertDTO(" londres", false),
                    new AnswerUpsertDTO(" berlin", false),
                    new AnswerUpsertDTO(" madrid", false)
            ));

            // When
            QuestionAdminDTO createdQuestion = questionService.create(quizId, questionInsertDTO);

            // Then
            Question question = questionRepository.findById(createdQuestion.id()).get();
            assertThat(question).isNotNull();
            assertThat(question.getText()).isEqualTo("Question");
            assertThat(question.getAnswers().size()).isEqualTo(4);
            assertThat(question.getAnswers().getFirst()).isNotNull();
            assertThat(question.getAnswers().getFirst().getText()).isEqualTo("Paris");
            assertThat(question.getAnswers().getFirst().getIsCorrect()).isTrue();
            assertThat(question.getQuiz().getId()).isEqualTo(quizId);
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void shouldFailToCreate_WhenQuizNotFound() {
            // Given
            long quizId = 9999L;
            QuestionInsertDTO questionInsertDTO = new QuestionInsertDTO(" question", List.of(
                    new AnswerUpsertDTO(" paris", true),
                    new AnswerUpsertDTO(" londres", false),
                    new AnswerUpsertDTO(" berlin", false),
                    new AnswerUpsertDTO(" madrid", false)
            ));

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.create(quizId, questionInsertDTO));
        }

        @Test
        @DisplayName("Erreur - Question déjà existante pour le quiz")
        public void shouldFailToCreate_WhenQuestionAlreadyExists() {
            // Given
            long quizId = 1L;
            QuestionInsertDTO questionInsertDTO = new QuestionInsertDTO(" qui est le fondateur du stoïcisme ?", List.of(
                    new AnswerUpsertDTO("Paris", true),
                    new AnswerUpsertDTO("Londres", false),
                    new AnswerUpsertDTO("Berlin", false),
                    new AnswerUpsertDTO("Madrid", false)
            ));

            // When / Then
            assertThrows(AlreadyExistException.class, () -> questionService.create(quizId, questionInsertDTO));
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
            QuestionUpdateDTO questionInsertDTO = new QuestionUpdateDTO(" question");

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
            QuestionUpdateDTO questionInsertDTO = new QuestionUpdateDTO(" question");

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.update(questionId, questionInsertDTO));
        }

        @Test
        @Transactional
        @DisplayName("Erreur - Question déjà existante pour le quiz")
        public void shouldFailToUpdate_WhenQuestionAlreadyExists() {
            // Given
            long questionId = 1L;
            QuestionUpdateDTO questionInsertDTO = new QuestionUpdateDTO(" qui est le fondateur du stoïcisme ?");

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
            assertThat(answerRepository.existsByQuestionId(questionId)).isFalse();

            Quiz quiz = quizRepository.findById(1L).get();
            assertThat(quiz.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Succès - Quiz désactivé")
        public void shouldDeleteQuestionAndDisableQuiz() {

            // Given / When
            for (int id = 20; id >= MINIMUM_QUIZ_QUESTIONS; id--) {
                questionService.delete(id);
            }

            // Then
            Quiz quiz = quizRepository.findById(1L).get();
            assertThat(quiz.getDisabledAt()).isNotNull();
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
        @DisplayName("Succès - Question désactivée & Quiz désactivé")
        public void shouldDisableQuestionAndDisableQuiz() {
            // Given / When
            for (int id = 20; id >= MINIMUM_QUIZ_QUESTIONS; id--) {
                questionService.updateVisibility(id, false);
            }

            // Then
            Quiz quiz = quizRepository.findById(1L).get();
            assertThat(quiz.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Question activée")
        public void shouldEnableQuestion() {
            // Given
            long questionId = 1L;
            questionService.updateVisibility(questionId, false);

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
