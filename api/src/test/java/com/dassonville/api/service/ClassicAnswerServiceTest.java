package com.dassonville.api.service;


import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.QuestionType;
import com.dassonville.api.repository.ClassicAnswerRepository;
import com.dassonville.api.repository.GameRuleRepository;
import com.dassonville.api.repository.QuestionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Tag("service")
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Réponse")
public class ClassicAnswerServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ClassicAnswerRepository classicAnswerRepository;
    @Mock
    private GameRuleRepository gameRuleRepository;


    private AnswerService answerService;

    private final AnswerMapper answerMapper = Mappers.getMapper(AnswerMapper.class);

    private long id;
    private ClassicAnswer classicAnswer;
    private ClassicAnswerUpsertDTO classicAnswerUpsertDTO;


    @BeforeEach
    void setUp() {
        answerService = new AnswerService(classicAnswerRepository, answerMapper, questionRepository, gameRuleRepository);

        id = 1L;

        Question question = Question.builder()
                .id(id)
                .questionType(new QuestionType("CLASSIC"))
                .build();

        classicAnswer = ClassicAnswer.builder()
                .id(id)
                .text("ClassicAnswer")
                .isCorrect(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .question(question)
                .build();

        classicAnswerUpsertDTO = new ClassicAnswerUpsertDTO(" classicAnswer", true);
    }


    @Nested
    @DisplayName("Créer une réponse")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newAnswer() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(true);
            when(classicAnswerRepository.existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong()))
                    .thenReturn(false);
            when(classicAnswerRepository.save(any(ClassicAnswer.class)))
                    .thenReturn(classicAnswer);

            ArgumentCaptor<ClassicAnswer> captor = ArgumentCaptor.forClass(ClassicAnswer.class);

            // When
            AnswerAdminDTO result = answerService.create(id, classicAnswerUpsertDTO);

            // Then
            assertAll("Verify method calls",
                    () -> verify(questionRepository).existsById(id),
                    () -> verify(questionRepository).existsByIdAndQuestionTypeCode(id, AppConstants.CLASSIC_TYPE),
                    () -> verify(classicAnswerRepository).existsByTextIgnoreCaseAndQuestionId(classicAnswer.getText(), id),
                    () -> verify(classicAnswerRepository).save(captor.capture())
            );

            ClassicAnswer capturedAnswer = captor.getValue();

            assertAll("Verify saved entity",
                    () -> assertThat(capturedAnswer.getText()).isEqualTo(classicAnswer.getText()),
                    () -> assertThat(capturedAnswer.getIsCorrect()).isTrue(),
                    () -> assertThat(capturedAnswer.getQuestion().getId()).isEqualTo(id)
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.text()).isEqualTo(classicAnswer.getText()),
                    () -> assertThat(result.isCorrect()).isEqualTo(classicAnswer.getIsCorrect()),
                    () -> assertThat(result.createdAt()).isEqualTo(classicAnswer.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(classicAnswer.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(classicAnswer.getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void create_questionNotFound() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> answerService.create(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_NOT_FOUND),
                    () -> verify(questionRepository).existsById(id),
                    () -> verifyNoMoreInteractions(questionRepository),
                    () -> verifyNoInteractions(classicAnswerRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Type de question incorrect")
        void create_incorrectQuestionType() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.create(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_TYPE_NOT_SUPPORTED),
                    () -> verify(questionRepository).existsById(id),
                    () -> verify(questionRepository).existsByIdAndQuestionTypeCode(id, AppConstants.CLASSIC_TYPE),
                    () -> verifyNoInteractions(classicAnswerRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante pour la question")
        void create_answerAlreadyExists() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(questionRepository.existsByIdAndQuestionTypeCode(anyLong(), anyString()))
                    .thenReturn(true);
            when(classicAnswerRepository.existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> answerService.create(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_ALREADY_EXISTS),
                    () -> verify(questionRepository).existsById(id),
                    () -> verify(questionRepository).existsByIdAndQuestionTypeCode(id, AppConstants.CLASSIC_TYPE),
                    () -> verify(classicAnswerRepository).existsByTextIgnoreCaseAndQuestionId(anyString(), eq(id)),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );
        }
    }


    @Nested
    @DisplayName("Mettre à jour une réponse")
    class UpdateTest {

        private ClassicAnswer classicAnswerUpdated;
        private ClassicAnswerUpsertDTO classicAnswerToUpdateDTO;

        @BeforeEach
        void init() {
            classicAnswerUpdated = ClassicAnswer.builder()
                    .id(id)
                    .text("Updated ClassicAnswer")
                    .isCorrect(classicAnswer.getIsCorrect())
                    .createdAt(classicAnswer.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(classicAnswer.getDisabledAt())
                    .question(classicAnswer.getQuestion())
                    .build();

            classicAnswerToUpdateDTO = new ClassicAnswerUpsertDTO(" updated ClassicAnswer  ", true);
        }

        @Test
        @DisplayName("Succès")
        void update_answer() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);

            // When
            AnswerAdminDTO result = answerService.update(id, classicAnswerToUpdateDTO);

            // Then
            assertAll("Verify method calls",
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).existsByTextIgnoreCaseAndIdNot(classicAnswer.getText(), id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );

            assertAll("Verify updated entity",
                    () -> assertThat(classicAnswer.getText()).isEqualTo(classicAnswerUpdated.getText()),
                    () -> assertThat(classicAnswer.getIsCorrect()).isEqualTo(classicAnswerUpdated.getIsCorrect())
            );

            assertAll("Assertions for DTO",
                    () -> assertThat(result.id()).isEqualTo(classicAnswer.getId()),
                    () -> assertThat(result.text()).isEqualTo(classicAnswer.getText()),
                    () -> assertThat(result.isCorrect()).isEqualTo(classicAnswer.getIsCorrect()),
                    () -> assertThat(result.createdAt()).isEqualTo(classicAnswer.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(classicAnswer.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(classicAnswer.getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void update_answerNotFound() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> answerService.update(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_NOT_FOUND),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        void update_answerAlreadyExists() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> answerService.update(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_ALREADY_EXISTS),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).existsByTextIgnoreCaseAndIdNot(classicAnswer.getText(), id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Plus de réponse correcte active")
        void update_noCorrectAnswer() {
            // Given
            classicAnswerUpsertDTO = new ClassicAnswerUpsertDTO(" updated ClassicAnswer  ", false);

            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.update(id, classicAnswerUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).existsByTextIgnoreCaseAndIdNot(anyString(), eq(id)),
                    () -> verify(classicAnswerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(classicAnswer.getQuestion().getId(), classicAnswer.getId())
            );
        }
    }


    @Nested
    @DisplayName("Supprimer une réponse")
    class DeleteTest {

        @Test
        @DisplayName("Succès")
        void delete_answer() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(2);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 1));
            when(classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(true);

            // When
            answerService.delete(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verify(classicAnswerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(classicAnswer.getQuestion().getId(), classicAnswer.getId()),
                    () -> verify(classicAnswerRepository).delete(classicAnswer)
            );
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void delete_answerNotFound() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> answerService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_NOT_FOUND),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository),
                    () -> verifyNoInteractions(gameRuleRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Pas assez de réponses actives")
        void delete_notEnoughActiveAnswers() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 2));

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.MINIMUM_ACTIVE_ANSWERS_NOT_REACHED),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Pas de réponse correcte active")
        void delete_noActiveCorrectAnswer() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(2);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 1));
            when(classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verify(classicAnswerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(classicAnswer.getQuestion().getId(), classicAnswer.getId()),
                    () -> verifyNoMoreInteractions(classicAnswerRepository)
            );
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'une réponse")
    class UpdateVisibilityTest {

        @Test
        @DisplayName("Succès")
        void toggleVisibility_answer() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(3);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 2));
            when(classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(true);

            // When
            answerService.updateVisibility(id, false);

            // Then
            assertAll("Verify method calls",
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verify(classicAnswerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(classicAnswer.getQuestion().getId(), classicAnswer.getId()),
                    () -> assertThat(classicAnswer.isVisible()).isFalse()
            );
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void toggleVisibility_answerNotFound() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> answerService.updateVisibility(id, false));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ANSWER_NOT_FOUND),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository),
                    () -> verifyNoInteractions(gameRuleRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Pas assez de réponses actives")
        void toggleVisibility_notEnoughActiveAnswers() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 2));

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(id, false));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.MINIMUM_ACTIVE_ANSWERS_NOT_REACHED),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verifyNoMoreInteractions(classicAnswerRepository),
                    () -> assertThat(classicAnswer.isVisible()).isTrue()
            );
        }

        @Test
        @DisplayName("Erreur - Pas de réponse correcte active")
        void toggleVisibility_noActiveCorrectAnswer() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));
            when(classicAnswerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(3);
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(anyString()))
                    .thenReturn(Optional.of((byte) 2));
            when(classicAnswerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(id, false));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY),
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verify(classicAnswerRepository).countByQuestionIdAndDisabledAtIsNull(classicAnswer.getQuestion().getId()),
                    () -> verify(gameRuleRepository).findMaxAnswerOptionsCountByQuestionTypeCode(classicAnswer.getQuestion().getQuestionType().getCode()),
                    () -> verify(classicAnswerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(classicAnswer.getQuestion().getId(), classicAnswer.getId()),
                    () -> verifyNoMoreInteractions(classicAnswerRepository),
                    () -> assertThat(classicAnswer.isVisible()).isTrue()
            );
        }

        @Test
        @DisplayName("Même statut - Pas de changement de visibilité")
        void toggleVisibility_noChange() {
            // Given
            when(classicAnswerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(classicAnswer));

            // When
            answerService.updateVisibility(id, true);

            // Then
            assertAll("Verify method calls",
                    () -> verify(classicAnswerRepository).findById(id),
                    () -> verifyNoMoreInteractions(classicAnswerRepository),
                    () -> verifyNoInteractions(gameRuleRepository),
                    () -> assertThat(classicAnswer.isVisible()).isTrue()
            );
        }
    }
}
