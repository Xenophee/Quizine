package com.dassonville.api.service;


import com.dassonville.api.constant.RequestActionType;
import com.dassonville.api.constant.Type;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionInsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionUpdateDTO;
import com.dassonville.api.dto.request.ThemeUpsertDTO;
import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuestionTypeRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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
    private QuestionTypeRepository questionTypeRepository;

    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;


    private long id;
    private Question question;
    private ClassicQuestionInsertDTO classicQuestionInsertDTO;
    private ClassicQuestionUpdateDTO classicQuestionUpdateDTO;


    @BeforeEach
    void setUp() {

        questionService = new QuestionService(questionRepository, questionMapper, quizRepository, questionTypeRepository);

        id = 1L;

        ClassicAnswer classicAnswer = ClassicAnswer.builder()
                .id(1L)
                .text("ClassicAnswer 1")
                .isCorrect(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .build();

        ClassicAnswer classicAnswer2 = ClassicAnswer.builder()
                .id(2L)
                .text("ClassicAnswer 2")
                .isCorrect(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .build();


        question = Question.builder()
                .id(id)
                .text("Question text")
                .answerExplanation("Question explanation")
                .answerIfTrueFalse(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .classicAnswers(List.of(classicAnswer, classicAnswer2))
                .build();

        question.setQuizzes(List.of(
                Quiz.builder()
                        .id(10L)
                        .title("Quiz Title")
                        .description("Quiz Description")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .disabledAt(null)
                        .questions(new ArrayList<>())
                        .build()
        ));

        classicQuestionInsertDTO = new ClassicQuestionInsertDTO(
                RequestActionType.CLASSIC_INSERT,
                " question text  ",
                " explication  ",
                List.of(
                new ClassicAnswerUpsertDTO(" classicAnswer 1  ", true),
                new ClassicAnswerUpsertDTO(" classicAnswer 2  ", false)
        ));

        classicQuestionUpdateDTO = new ClassicQuestionUpdateDTO(
                RequestActionType.CLASSIC_UPDATE,
                " updated text  ",
                " updated explanation  "
        );
    }


    @Nested
    @DisplayName("Créer une question")
    class CreateQuestion {

        @Test
        @DisplayName("Succès")
        void createQuestion() {
            // Given
            when(quizRepository.findById(id))
                    .thenReturn(Optional.ofNullable(question.getQuizzes().getFirst()));
            when(questionTypeRepository.existsByCodeAndQuizTypes_Quizzes_Id(anyString(), anyLong()))
                    .thenReturn(true);
            when(questionRepository.existsByQuizzesIdAndTextIgnoreCase(anyLong(), anyString()))
                    .thenReturn(false);
            when(questionRepository.save(any(Question.class)))
                    .thenReturn(question);

            // When
            QuestionAdminDTO result = questionService.create(id, classicQuestionInsertDTO);
            AnswerAdminDTO resultAnswer = result.answers().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).findById(id),
                    () -> verify(questionTypeRepository).existsByCodeAndQuizTypes_Quizzes_Id(eq(Type.CLASSIC.getType()), eq(id)),
                    () -> verify(questionRepository).existsByQuizzesIdAndTextIgnoreCase(anyLong(), anyString()),
                    () -> verify(questionRepository).save(any(Question.class))
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(question.getId()),
                    () -> assertThat(result.text()).isEqualTo(question.getText()),
                    () -> assertThat(result.answerExplanation()).isEqualTo(question.getAnswerExplanation()),
                    () -> assertThat(result.answerIfTrueFalse()).isEqualTo(question.getAnswerIfTrueFalse()),
                    () -> assertThat(result.createdAt()).isEqualTo(question.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(question.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(question.getDisabledAt()),

                    () -> assertThat(result.answers()).hasSize(question.getClassicAnswers().size()),
                    () -> assertThat(resultAnswer.text()).isEqualTo(question.getClassicAnswers().getFirst().getText()),
                    () -> assertThat(resultAnswer.isCorrect()).isEqualTo(question.getClassicAnswers().getFirst().getIsCorrect()),
                    () -> assertThat(resultAnswer.createdAt()).isEqualTo(question.getClassicAnswers().getFirst().getCreatedAt()),
                    () -> assertThat(resultAnswer.updatedAt()).isEqualTo(question.getClassicAnswers().getFirst().getUpdatedAt()),
                    () -> assertThat(resultAnswer.disabledAt()).isEqualTo(question.getClassicAnswers().getFirst().getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void createQuestion_QuizNotFound() {
            // Given
            when(quizRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> questionService.create(id, classicQuestionInsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).findById(id),
                    () -> verifyNoInteractions(questionTypeRepository),
                    () -> verifyNoInteractions(questionRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Type de question non supporté")
        void createQuestion_WrongType() {
            // Given
            when(quizRepository.findById(id))
                    .thenReturn(Optional.ofNullable(question.getQuizzes().getFirst()));
            when(questionTypeRepository.existsByCodeAndQuizTypes_Quizzes_Id(anyString(), anyLong()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> questionService.create(id, classicQuestionInsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_TYPE_NOT_SUPPORTED),
                    () -> verify(quizRepository).findById(id),
                    () -> verify(questionTypeRepository).existsByCodeAndQuizTypes_Quizzes_Id(eq(Type.CLASSIC.getType()), eq(id)),
                    () -> verifyNoInteractions(questionRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Question déjà existante")
        void createQuestion_AlreadyExists() {
            // Given
            when(quizRepository.findById(id))
                    .thenReturn(Optional.ofNullable(question.getQuizzes().getFirst()));
            when(questionTypeRepository.existsByCodeAndQuizTypes_Quizzes_Id(anyString(), anyLong()))
                    .thenReturn(true);
            when(questionRepository.existsByQuizzesIdAndTextIgnoreCase(anyLong(), anyString()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> questionService.create(id, classicQuestionInsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_ALREADY_EXISTS),
                    () -> verify(quizRepository).findById(id),
                    () -> verify(questionTypeRepository).existsByCodeAndQuizTypes_Quizzes_Id(eq(Type.CLASSIC.getType()), eq(id)),
                    () -> verify(questionRepository).existsByQuizzesIdAndTextIgnoreCase(eq(id), anyString()),
                    () -> verifyNoMoreInteractions(questionRepository)
            );
        }
    }


    @Nested
    @DisplayName("Mettre à jour une question")
    class UpdateQuestion {

        private Question questionUpdated;
        private ClassicQuestionUpdateDTO classicQuestionUpdateDTO;

        @BeforeEach
        void setUp() {
            // Reset the question object to ensure it is not modified by previous tests
            questionUpdated = Question.builder()
                    .id(id)
                    .text("Updated text")
                    .answerExplanation("Updated explanation")
                    .answerIfTrueFalse(null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(null)
                    .classicAnswers(new ArrayList<>(question.getClassicAnswers()))
                    .build();

            classicQuestionUpdateDTO = new ClassicQuestionUpdateDTO(
                    RequestActionType.CLASSIC_UPDATE,
                    " updated text  ",
                    " updated explanation  "
            );
        }

        @Test
        @DisplayName("Succès")
        void updateQuestion() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(true);
            when(questionRepository.existsByQuizzesIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong()))
                    .thenReturn(false);

            // When
            QuestionAdminDTO result = questionService.update(id, classicQuestionUpdateDTO);
            AnswerAdminDTO resultAnswer = result.answers().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(questionRepository).findById(id),
                    () -> verify(questionRepository).existsByQuizzesIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong())
            );

            assertAll("Verify updated entity",
                    () -> assertThat(question.getText()).isEqualTo(questionUpdated.getText()),
                    () -> assertThat(question.getAnswerExplanation()).isEqualTo(questionUpdated.getAnswerExplanation())
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(question.getId()),
                    () -> assertThat(result.text()).isEqualTo(question.getText()),
                    () -> assertThat(result.answerExplanation()).isEqualTo(question.getAnswerExplanation()),
                    () -> assertThat(result.answerIfTrueFalse()).isEqualTo(question.getAnswerIfTrueFalse()),
                    () -> assertThat(result.createdAt()).isEqualTo(question.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(question.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(question.getDisabledAt()),

                    () -> assertThat(result.answers()).hasSize(question.getClassicAnswers().size()),
                    () -> assertThat(resultAnswer.text()).isEqualTo(question.getClassicAnswers().getFirst().getText()),
                    () -> assertThat(resultAnswer.isCorrect()).isEqualTo(question.getClassicAnswers().getFirst().getIsCorrect()),
                    () -> assertThat(resultAnswer.createdAt()).isEqualTo(question.getClassicAnswers().getFirst().getCreatedAt()),
                    () -> assertThat(resultAnswer.updatedAt()).isEqualTo(question.getClassicAnswers().getFirst().getUpdatedAt()),
                    () -> assertThat(resultAnswer.disabledAt()).isEqualTo(question.getClassicAnswers().getFirst().getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> questionService.update(id, classicQuestionUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_NOT_FOUND),
                    () -> verify(questionRepository).findById(id),
                    () -> verifyNoMoreInteractions(questionRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Question de type incorrect")
        void updateQuestion_WrongType() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> questionService.update(id, classicQuestionUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_AND_QUESTION_TYPE_MISMATCH),
                    () -> verify(questionRepository).findById(id),
                    () -> verify(questionRepository).existsByIdAndQuestionTypeCode(id, classicQuestionUpdateDTO.type().getType()),
                    () -> verifyNoMoreInteractions(questionRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Question déjà existante")
        void updateQuestion_AlreadyExists() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.of(question));
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(true);
            when(questionRepository.existsByQuizzesIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), anyLong()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> questionService.update(id, classicQuestionUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_ALREADY_EXISTS),
                    () -> verify(questionRepository).findById(id),
                    () -> verify(questionRepository).existsByQuizzesIdAndTextIgnoreCaseAndIdNot(anyLong(), anyString(), eq(id))
            );
        }
    }


    @Nested
    @DisplayName("Supprimer une question")
    class DeleteQuestion {

        @Test
        @DisplayName("Succès")
        void deleteQuestion() {
            // Given
            when(questionRepository.existsById(id))
                    .thenReturn(true);

            // When
            questionService.delete(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(questionRepository).existsById(id),
                    () -> verify(questionRepository).deleteById(id)
            );
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void deleteQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.existsById(id))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> questionService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_NOT_FOUND),
                    () -> verify(questionRepository).existsById(id),
                    () -> verifyNoMoreInteractions(questionRepository)
            );
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

            // When
            questionService.updateVisibility(id, false);

            // Then
            assertAll(
                    () -> verify(questionRepository).findById(id),
                    () -> assertThat(question.isVisible()).isFalse()
            );
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateVisibilityQuestion_QuestionNotFound() {
            // Given
            when(questionRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> questionService.updateVisibility(id, true));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_NOT_FOUND),
                    () -> verify(questionRepository).findById(id)
            );
        }
    }
}
