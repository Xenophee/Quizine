package com.dassonville.api.service;


import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Question")
public class QuestionServiceTest {

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

        questionService = new QuestionService(questionRepository, questionMapper, quizRepository, quizService);

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
                    .thenThrow(NotFoundException.class);

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
                    .thenThrow(NotFoundException.class);

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
                    .thenThrow(NotFoundException.class);

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
