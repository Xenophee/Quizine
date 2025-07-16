package com.dassonville.api.service;


import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.request.ClassicChoiceAnswerRequestDTO;
import com.dassonville.api.dto.request.ClassicTextAnswerRequestDTO;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.response.CheckAnswerResultDTO;
import com.dassonville.api.dto.response.CheckClassicAnswerResultDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.exception.MisconfiguredException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.repository.*;
import com.dassonville.api.service.checker.QuestionAnswerChecker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@Tag("service")
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Game")
public class GameServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuizTypeRepository quizTypeRepository;
    @Mock
    private QuestionTypeRepository questionTypeRepository;
    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;
    @Mock
    private List<QuestionAnswerChecker> checkers;

    @InjectMocks
    private GameService gameService;

    private ClassicChoiceAnswerRequestDTO questionAnswerRequestDTO;
    private ClassicTextAnswerRequestDTO classicTextAnswerRequestDTO;


    @BeforeEach
    void setUp() {
        questionAnswerRequestDTO = new ClassicChoiceAnswerRequestDTO(
                GameType.CLASSIC_CHOICE,
                1L,
                1L,
                1L,
                true,
                true,
                List.of(101L, 102L),
                10
        );

        classicTextAnswerRequestDTO = new ClassicTextAnswerRequestDTO(
                GameType.CLASSIC_TEXT,
                1L,
                1L,
                1L,
                true,
                true,
                List.of("Paris", "Londres"),
                10
        );
    }



    @Test
    @DisplayName("Échec - Question non trouvée ou désactivée")
    void shouldThrowNotFoundException_WhenQuestionNotFound() {
        // Given
        QuestionAnswerRequestDTO request = mock(QuestionAnswerRequestDTO.class);
        when(request.questionId()).thenReturn(1L);
        when(request.quizId()).thenReturn(1L);
        when(questionRepository.existsByIdAndDisabledAtIsNullEverywhere(1L, 1L)).thenReturn(false);

        // When / Then
        assertThatThrownBy(() -> gameService.checkAnswer(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.QUESTION_AND_QUIZ_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("Échec - Type de quiz non trouvé")
    void shouldThrowNotFoundException_WhenQuizTypeNotFound() {
        // Given
        QuestionAnswerRequestDTO request = mock(QuestionAnswerRequestDTO.class);
        when(request.quizId()).thenReturn(1L);
        when(questionRepository.existsByIdAndDisabledAtIsNullEverywhere(anyLong(), anyLong())).thenReturn(true);
        when(quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> gameService.checkAnswer(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.QUIZ_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Échec - Type de quiz non conforme")
    void shouldThrowMisconfiguredException_WhenQuizTypeMismatch() {
        // Given
        QuestionAnswerRequestDTO request = mock(QuestionAnswerRequestDTO.class);
        when(request.quizId()).thenReturn(1L);
        when(request.type()).thenReturn(mock(GameType.class));
        when(request.type().getMainType()).thenReturn("EXPECTED_TYPE");
        when(questionRepository.existsByIdAndDisabledAtIsNullEverywhere(anyLong(), anyLong())).thenReturn(true);
        when(quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(1L)).thenReturn(Optional.of("ACTUAL_TYPE"));

        // When / Then
        assertThatThrownBy(() -> gameService.checkAnswer(request))
                .isInstanceOf(MisconfiguredException.class)
                .hasMessage(ErrorCode.QUIZ_TYPE_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("Échec - Vérificateur non trouvé")
    void shouldThrowInvalidStateException_WhenCheckerNotFound() {
        // Given
        QuestionAnswerRequestDTO request = mock(QuestionAnswerRequestDTO.class);
        when(request.quizId()).thenReturn(1L);
        when(request.type()).thenReturn(mock(GameType.class));
        when(request.type().getMainType()).thenReturn("EXPECTED_TYPE");
        when(questionRepository.existsByIdAndDisabledAtIsNullEverywhere(anyLong(), anyLong())).thenReturn(true);
        when(quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(1L)).thenReturn(Optional.of("EXPECTED_TYPE"));
        when(questionTypeRepository.findIdsByQuizTypeCode("EXPECTED_TYPE")).thenReturn(List.of("TYPE_1"));
        when(questionTypeRepository.existsByIdAndDifficultyLevelsId(anyString(), anyLong())).thenReturn(true);
        when(checkers.stream()).thenReturn(Stream.of());

        // When / Then
        assertThatThrownBy(() -> gameService.checkAnswer(request))
                .isInstanceOf(InvalidStateException.class)
                .hasMessage(ErrorCode.CHECK_ANSWER_TYPE_NOT_SUPPORTED.getMessage());
    }

    @Test
    @DisplayName("Succès - Vérification de la réponse")
    void shouldReturnCheckAnswerResult_WhenValidRequest() {
        // Given
        QuestionAnswerRequestDTO request = mock(QuestionAnswerRequestDTO.class);
        QuestionAnswerChecker checker = mock(QuestionAnswerChecker.class);
        CheckAnswerResultDTO expectedResult = mock(CheckAnswerResultDTO.class);

        when(request.quizId()).thenReturn(1L);
        when(request.type()).thenReturn(mock(GameType.class));
        when(request.type().getMainType()).thenReturn("EXPECTED_TYPE");
        when(questionRepository.existsByIdAndDisabledAtIsNullEverywhere(anyLong(), anyLong())).thenReturn(true);
        when(quizTypeRepository.findQuizTypeCodeByQuizIdAndDisabledAtIsNull(1L)).thenReturn(Optional.of("EXPECTED_TYPE"));
        when(questionTypeRepository.findIdsByQuizTypeCode("EXPECTED_TYPE")).thenReturn(List.of("TYPE_1"));
        when(questionTypeRepository.existsByIdAndDifficultyLevelsId(anyString(), anyLong())).thenReturn(true);
        when(checkers.stream()).thenReturn(Stream.of(checker));
        when(checker.supports(request.type())).thenReturn(true);
        when(checker.checkAnswer(request, true)).thenReturn(expectedResult);

        // When
        CheckAnswerResultDTO result = gameService.checkAnswer(request);

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }


}
