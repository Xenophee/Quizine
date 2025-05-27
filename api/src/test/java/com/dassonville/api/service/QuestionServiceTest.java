package com.dassonville.api.service;


import com.dassonville.api.dto.*;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Question")
public class QuestionServiceTest {

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuizService quizService;

    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;


    private long id;
    private Question question;
    private QuestionInsertDTO questionInsertDTO;
    private QuestionUpdateDTO questionUpdateDTO;


    @BeforeEach
    void setUp() {

        questionService = new QuestionService(questionRepository, questionMapper, quizRepository, quizService, answerRepository);

        id = 1L;

        Answer answer = new Answer();
        answer.setId(1L);
        answer.setText("Answer 1");
        answer.setIsCorrect(true);

        Answer answer2 = new Answer();
        answer2.setId(2L);
        answer2.setText("Answer 2");
        answer2.setIsCorrect(false);

        question = new Question();
        question.setId(id);
        question.setText("Question text");
        question.setAnswers(List.of(answer, answer2));
        question.setQuiz(new Quiz(10L));

        questionInsertDTO = new QuestionInsertDTO(" question text", List.of(
                new AnswerUpsertDTO(" answer 1", true),
                new AnswerUpsertDTO(" answer 2", false)
        ));
        questionUpdateDTO = new QuestionUpdateDTO(" updated text");
    }


    @Nested
    @DisplayName("Vérifier la réponse à une question par choix")
    class CheckChoicesAnswer {

        @Test
        @DisplayName("Succès - Réponses correctes")
        void shouldReturnCorrectResult_WhenAnswersAreCorrect() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<Long> submittedAnswerIds = List.of(101L, 102L);

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.countActiveValidAnswers(submittedAnswerIds, questionId))
                    .thenReturn(submittedAnswerIds.size());
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of(
                            new Answer(101L, "Answer 1", true),
                            new Answer(102L, "Answer 2", true)
                    ));

            // When
            CheckAnswerResultDTO result = questionService.checkAnswerByChoice(quizId, questionId, submittedAnswerIds);

            // Then
            assertThat(result.isCorrect()).isTrue();
            assertThat(result.correctAnswers().size()).isEqualTo(2);

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).countActiveValidAnswers(submittedAnswerIds, questionId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }

        @Test
        @DisplayName("Succès - Réponses incorrectes")
        void shouldReturnIncorrectResult_WhenAnswersAreIncorrect() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<Long> submittedAnswerIds = List.of(101L, 103L);

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.countActiveValidAnswers(submittedAnswerIds, questionId))
                    .thenReturn(2);
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of(
                            new Answer(101L, "Answer 1", true),
                            new Answer(102L, "Answer 2", true)
                    ));

            // When
            CheckAnswerResultDTO result = questionService.checkAnswerByChoice(quizId, questionId, submittedAnswerIds);

            // Then
            assertThat(result.isCorrect()).isFalse();
            assertThat(result.correctAnswers().size()).isEqualTo(2);

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).countActiveValidAnswers(submittedAnswerIds, questionId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void shouldThrowNotFoundException_WhenQuestionDoesNotExist() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<Long> submittedAnswerIds = List.of(101L, 102L);

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(false);

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.checkAnswerByChoice(quizId, questionId, submittedAnswerIds));

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verifyNoInteractions(answerRepository);
        }

        @Test
        @DisplayName("Erreur - Réponses soumises invalides")
        void shouldThrowIllegalArgumentException_WhenSubmittedAnswersAreInvalid() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<Long> submittedAnswerIds = List.of(101L, 102L);

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.countActiveValidAnswers(submittedAnswerIds, questionId))
                    .thenReturn(1); // Un seul ID soumis appartient à la question

            // When / Then
            assertThrows(IllegalArgumentException.class, () -> questionService.checkAnswerByChoice(quizId, questionId, submittedAnswerIds));

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).countActiveValidAnswers(submittedAnswerIds, questionId);
            verifyNoMoreInteractions(answerRepository);
        }

        @Test
        @DisplayName("Erreur - Pas de bonnes réponses")
        void shouldThrowIllegalStateException_WhenNoCorrectAnswersExist() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<Long> submittedAnswerIds = List.of(101L, 102L);

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.countActiveValidAnswers(submittedAnswerIds, questionId))
                    .thenReturn(submittedAnswerIds.size());
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of()); // Aucune bonne réponse

            // When / Then
            assertThrows(IllegalStateException.class, () -> questionService.checkAnswerByChoice(quizId, questionId, submittedAnswerIds));

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).countActiveValidAnswers(submittedAnswerIds, questionId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }
    }


    @Nested
    @DisplayName("Vérifier la réponse à une question par texte")
    class CheckTextAnswer {

        @Test
        @DisplayName("Succès - Réponses correctes")
        void shouldReturnCorrectResult_WhenAnswersAreCorrect() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<String> submittedAnswers = List.of(" paris  ", " londres");

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of(
                            new Answer(1L, "Paris", true),
                            new Answer(2L, "Londres", true)
                    ));

            // When
            CheckAnswerResultDTO result = questionService.checkAnswerByText(quizId, questionId, submittedAnswers);

            // Then
            assertThat(result.isCorrect()).isTrue();
            assertThat(result.correctAnswers().size()).isEqualTo(2);

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }

        @Test
        @DisplayName("Succès - Réponses incorrectes")
        void shouldReturnIncorrectResult_WhenAnswersAreWrong() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<String> submittedAnswers = List.of("Berlin", " paris");

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of(
                            new Answer(1L, "Paris", true),
                            new Answer(2L, "Londres", true)
                    ));

            // When
            CheckAnswerResultDTO result = questionService.checkAnswerByText(quizId, questionId, submittedAnswers);

            // Then
            assertThat(result.isCorrect()).isFalse();
            assertThat(result.correctAnswers().size()).isEqualTo(2);

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void shouldThrowNotFoundException_WhenQuestionDoesNotExist() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<String> submittedAnswers = List.of("Paris");

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(false);

            // When / Then
            assertThrows(NotFoundException.class, () -> questionService.checkAnswerByText(quizId, questionId, submittedAnswers));

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verifyNoInteractions(answerRepository);
        }

        @Test
        @DisplayName("Erreur - Pas de bonnes réponses")
        void shouldThrowIllegalStateException_WhenNoCorrectAnswersExist() {
            // Given
            long quizId = 1L;
            long questionId = 1L;
            List<String> submittedAnswers = List.of("Paris");

            when(questionRepository.existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId))
                    .thenReturn(true);
            when(answerRepository.findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId))
                    .thenReturn(List.of());

            // When / Then
            assertThrows(IllegalStateException.class, () -> questionService.checkAnswerByText(quizId, questionId, submittedAnswers));

            verify(questionRepository).existsByIdAndDisabledAtIsNullAndQuizIdAndQuizDisabledAtIsNull(questionId, quizId);
            verify(answerRepository).findByQuestionIdAndIsCorrectTrueAndDisabledAtIsNull(questionId);
        }
    }


    @Nested
    @DisplayName("Créer une question")
    class CreateQuestion {

        @Test
        @DisplayName("Succès")
        void createQuestion() {
            // Given
            when(quizRepository.existsById(id))
                    .thenReturn(true);
            when(questionRepository.existsByQuizIdAndTextIgnoreCase(anyLong(), anyString()))
                    .thenReturn(false);
            when(questionRepository.save(any(Question.class)))
                    .thenReturn(question);

            // When
            QuestionAdminDTO result = questionService.create(id, questionInsertDTO);

            // Then
            verify(quizRepository).existsById(id);
            verify(questionRepository).existsByQuizIdAndTextIgnoreCase(anyLong(), anyString());
            verify(questionRepository).save(any(Question.class));

            assertThat(result).isNotNull();
            assertThat(result.text()).isEqualTo(question.getText());
            assertThat(result.answers().size()).isEqualTo(question.getAnswers().size());
            assertThat(result.answers().getFirst().text()).isEqualTo(question.getAnswers().getFirst().getText());
            assertThat(result.answers().getFirst().isCorrect()).isEqualTo(question.getAnswers().getFirst().getIsCorrect());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void createQuestion_QuizNotFound() {
            // Given
            when(quizRepository.existsById(id))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> questionService.create(id, questionInsertDTO));

            // Then
            verify(quizRepository).existsById(id);
            verify(questionRepository, never()).existsByQuizIdAndTextIgnoreCase(anyLong(), anyString());
            verify(questionRepository, never()).save(any(Question.class));
        }

        @Test
        @DisplayName("Erreur - Question déjà existante")
        void createQuestion_AlreadyExists() {
            // Given
            when(quizRepository.existsById(id))
                    .thenReturn(true);
            when(questionRepository.existsByQuizIdAndTextIgnoreCase(anyLong(), anyString()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> questionService.create(id, questionInsertDTO));

            // Then
            verify(quizRepository).existsById(id);
            verify(questionRepository).existsByQuizIdAndTextIgnoreCase(anyLong(), anyString());
            verify(questionRepository, never()).save(any(Question.class));
        }
    }


    @Nested
    @DisplayName("Mettre à jour une question")
    class UpdateQuestion {

        @Test
        @DisplayName("Succès")
        void updateQuestion() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.existsByQuizIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong()))
                    .thenReturn(false);
            when(questionRepository.save(any(Question.class)))
                    .thenReturn(question);

            // When
            QuestionAdminDTO result = questionService.update(id, questionUpdateDTO);

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository).existsByQuizIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong());
            verify(questionRepository).save(any(Question.class));
            assertThat(result).isNotNull();
            assertThat(result.text()).isEqualTo(question.getText());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> questionService.update(id, questionUpdateDTO));

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository, never()).existsByQuizIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong());
            verify(questionRepository, never()).save(any(Question.class));
        }

        @Test
        @DisplayName("Erreur - Question déjà existante")
        void updateQuestion_AlreadyExists() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.existsByQuizIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> questionService.update(id, questionUpdateDTO));

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository).existsByQuizIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong());
            verify(questionRepository, never()).save(any(Question.class));
        }
    }


    @Nested
    @DisplayName("Supprimer une question")
    class DeleteQuestion {

        @Test
        @DisplayName("Succès - & Quiz actif")
        void deleteQuestion_QuizActive() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.countByQuizIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(10);

            // When
            questionService.delete(id);

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository).deleteById(id);
            verify(questionRepository).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Succès - & Quiz désactivée")
        void deleteQuestion() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.countByQuizIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(5);

            // When
            questionService.delete(id);

            // Then
            verify(questionRepository).findById(anyLong());
            verify(questionRepository).deleteById(anyLong());
            verify(questionRepository).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void deleteQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> questionService.delete(id));

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository, never()).deleteById(id);
            verify(questionRepository, never()).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService, never()).updateVisibility(anyLong(), anyBoolean());
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'une question")
    class UpdateVisibilityQuestion {

        @Test
        @DisplayName("Succès - Question désactivée")
        void updateVisibilityQuestion() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.save(any(Question.class)))
                    .thenReturn(question);
            when(questionRepository.countByQuizIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(10);

            // When
            questionService.updateVisibility(id, false);

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository).save(any(Question.class));
            verify(questionRepository).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateVisibilityQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> questionService.updateVisibility(id, true));

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository, never()).save(any(Question.class));
            verify(questionRepository, never()).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService, never()).updateVisibility(anyLong(), anyBoolean());
        }

        @Test
        @DisplayName("Erreur - Question déjà visible")
        void updateVisibilityQuestion_AlreadyVisible() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));

            // When
            questionService.updateVisibility(id, true);

            // Then
            verify(questionRepository).findById(id);
            verify(questionRepository, never()).save(any(Question.class));
            verify(questionRepository, never()).countByQuizIdAndDisabledAtIsNull(anyLong());
            verify(quizService, never()).updateVisibility(anyLong(), anyBoolean());
        }
    }
}
