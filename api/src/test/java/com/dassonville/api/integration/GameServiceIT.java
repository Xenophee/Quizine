package com.dassonville.api.integration;


import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.request.ClassicChoiceAnswerRequestDTO;
import com.dassonville.api.dto.request.ClassicTextAnswerRequestDTO;
import com.dassonville.api.dto.response.CheckClassicAnswerResultDTO;
import com.dassonville.api.dto.response.QuestionForPlayDTO;
import com.dassonville.api.exception.InvalidArgumentException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.projection.QuestionForPlayProjection;
import com.dassonville.api.repository.*;
import com.dassonville.api.service.GameService;
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
@DisplayName("IT - Service : Game")
public class GameServiceIT {

    @Autowired
    private GameService gameService;
    @Autowired
    private QuizTypeRepository quizTypeRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionTypeRepository questionTypeRepository;
    @Autowired
    private GameRuleRepository gameRuleRepository;
    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    
    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }



    @Nested
    @DisplayName("Récupération des questions d'un quiz pour le jeu")
    class GettingQuestionsForPlay {


        @ParameterizedTest
        @ValueSource(longs = {1L, 3L})
        @DisplayName("Succès - Récupération des questions d'un quiz pour le jeu")
        public void shouldFindAllQuestionsByQuizIdForPlay_WhenExistingQuiz(long difficultyId) {
            // Given
            long quizId = 11L;

            // When
            List<QuestionForPlayDTO> questions = gameService.findAllQuestionsByQuizIdForPlay(quizId, difficultyId);

            // Then
            assertThat(questions).isNotNull();
            assertThat(questions.size()).isEqualTo(20);

            // Vérification de l'ordre des IDs
            List<Long> originalOrder = questionRepository.findByQuizzesIdAndDisabledAtIsNullAndClassicAnswersDisabledAtIsNull(quizId)
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
            long difficultyId = 1L;

            // When / Then
            assertThrows(NotFoundException.class, () -> gameService.findAllQuestionsByQuizIdForPlay(idToGetQuiz, difficultyId));
        }

        @Test
        @DisplayName("Erreur - Difficulté non trouvée")
        public void shouldFailToFindAllQuestionsByQuizIdForPlay_WhenNonExistingDifficulty() {
            // Given
            long idToGetQuiz = 1L;
            long difficultyId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> gameService.findAllQuestionsByQuizIdForPlay(idToGetQuiz, difficultyId));
        }
    }

    @Nested
    @DisplayName("Vérification de la réponse à une question (choix)")
    class CheckingClassicAnswerByChoice {

        @Test
        @DisplayName("Succès - Réponse correcte")
        public void shouldCheckAnswerByChoice_Success() {
            // Given
            List<Long> submittedAnswerIds = List.of(922L, 923L); // Réponses correctes
            ClassicChoiceAnswerRequestDTO choiceAnswerRequest = new ClassicChoiceAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    291L, 
                    1L,
                    true,
                    true,
                    submittedAnswerIds,
                    10
            );

            // When
            CheckClassicAnswerResultDTO result = (CheckClassicAnswerResultDTO) gameService.checkAnswer(choiceAnswerRequest);

            // Then
            assertThat(result.isCorrect()).isTrue();
            assertThat(result.correctAnswers()).hasSize(2);
            assertThat(result.correctAnswers().getFirst().id()).isEqualTo(922L);
            assertThat(result.correctAnswers().getFirst().text()).isEqualTo("for");
            assertThat(result.correctAnswers().getLast().id()).isEqualTo(923L);
            assertThat(result.correctAnswers().getLast().text()).isEqualTo("while");
        }

        @Test
        @DisplayName("Succès - Réponse incorrecte")
        public void shouldCheckAnswerByChoice_IncorrectAnswer() {
            // Given
            List<Long> submittedAnswerIds = List.of(922L, 923L, 926L);
            ClassicChoiceAnswerRequestDTO choiceAnswerRequest = new ClassicChoiceAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    291L,
                    1L,
                    true,
                    true,
                    submittedAnswerIds,
                    10
            );

            // When
            CheckClassicAnswerResultDTO result = (CheckClassicAnswerResultDTO) gameService.checkAnswer(choiceAnswerRequest);

            // Then
            assertThat(result.isCorrect()).isFalse();
            assertThat(result.correctAnswers()).hasSize(2);
            assertThat(result.correctAnswers().getFirst().id()).isEqualTo(922L);
            assertThat(result.correctAnswers().getFirst().text()).isEqualTo("for");
            assertThat(result.correctAnswers().getLast().id()).isEqualTo(923L);
            assertThat(result.correctAnswers().getLast().text()).isEqualTo("while");
        }

        @Test
        @DisplayName("Échec - Réponse invalide")
        public void shouldCheckAnswerByChoice_InvalidAnswerId() {
            // Given
            List<Long> submittedAnswerIds = List.of(922L, 923L, 927L); // Réponse incorrecte (927 est true mais désactivée)
            ClassicChoiceAnswerRequestDTO choiceAnswerRequest = new ClassicChoiceAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    291L,
                    1L,
                    true,
                    true,
                    submittedAnswerIds,
                    10
            );

            // When / Then
            assertThrows(InvalidArgumentException.class, () -> gameService.checkAnswer(choiceAnswerRequest));
        }

        @Test
        @DisplayName("Échec - Question non trouvée")
        public void shouldCheckAnswerByChoice_QuestionNotFound() {
            // Given
            List<Long> submittedAnswerIds = List.of(1L);
            ClassicChoiceAnswerRequestDTO choiceAnswerRequest = new ClassicChoiceAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    1L,
                    9999L, // Question inexistante
                    1L,
                    true,
                    true,
                    submittedAnswerIds,
                    10
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> gameService.checkAnswer(choiceAnswerRequest));
        }
    }


    @Nested
    @DisplayName("Vérification de la réponse à une question (texte)")
    class CheckingClassicAnswerByText {

        @Test
        @DisplayName("Succès - Réponse correcte")
        public void shouldCheckAnswerByText_Success() {
            // Given
            List<String> submittedAnswers = List.of("for", " while  "); // Réponse correcte
            ClassicTextAnswerRequestDTO choiceAnswerRequest = new ClassicTextAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    291L,
                    1L,
                    true,
                    true,
                    submittedAnswers,
                    10
            );

            // When
            CheckClassicAnswerResultDTO result = (CheckClassicAnswerResultDTO) gameService.checkAnswer(choiceAnswerRequest);

            // Then
            assertThat(result.isCorrect()).isTrue();
            assertThat(result.correctAnswers()).hasSize(2);
            assertThat(result.correctAnswers().getFirst().id()).isEqualTo(922L);
            assertThat(result.correctAnswers().getFirst().text()).isEqualTo("for");
            assertThat(result.correctAnswers().getLast().id()).isEqualTo(923L);
            assertThat(result.correctAnswers().getLast().text()).isEqualTo("while");
        }

        @Test
        @DisplayName("Succès - Réponse incorrecte")
        public void shouldCheckAnswerByText_IncorrectAnswer() {
            // Given
            List<String> submittedAnswers = List.of("for", " while", "do-while    "); // Réponse incorrecte (do-while est true mais désactivée)
            ClassicTextAnswerRequestDTO choiceAnswerRequest = new ClassicTextAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    291L,
                    1L,
                    true,
                    true,
                    submittedAnswers,
                    10
            );

            // When
            CheckClassicAnswerResultDTO result = (CheckClassicAnswerResultDTO) gameService.checkAnswer(choiceAnswerRequest);

            // Then
            assertThat(result.isCorrect()).isFalse();
            assertThat(result.correctAnswers()).hasSize(2);
            assertThat(result.correctAnswers().getFirst().id()).isEqualTo(922L);
            assertThat(result.correctAnswers().getFirst().text()).isEqualTo("for");
            assertThat(result.correctAnswers().getLast().id()).isEqualTo(923L);
            assertThat(result.correctAnswers().getLast().text()).isEqualTo("while");
        }

        @Test
        @DisplayName("Échec - Question non trouvée")
        public void shouldCheckAnswerByText_QuestionNotFound() {
            // Given
            List<String> submittedAnswers = List.of("Réponse");
            ClassicTextAnswerRequestDTO choiceAnswerRequest = new ClassicTextAnswerRequestDTO(
                    GameType.CLASSIC_CHOICE,
                    16L,
                    290L, // Question désactivée
                    1L,
                    true,
                    true,
                    submittedAnswers,
                    10
            );

            // When / Then
            assertThrows(NotFoundException.class, () -> gameService.checkAnswer(choiceAnswerRequest));
        }
    }
}
