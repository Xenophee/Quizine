package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.constant.GameType;
import com.dassonville.api.dto.request.ClassicChoiceAnswerRequestDTO;
import com.dassonville.api.dto.request.ClassicTextAnswerRequestDTO;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.response.AnswerForPlayDTO;
import com.dassonville.api.dto.response.CheckClassicAnswerResultDTO;
import com.dassonville.api.dto.response.QuestionForPlayDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@DisplayName("IT - PUBLIC Controller : Game")
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GameService gameService;


    private long endpointId;
    private QuestionForPlayDTO questionForPlayDTO;
    private ClassicChoiceAnswerRequestDTO classicChoiceAnswerRequestDTO;
    private ClassicTextAnswerRequestDTO classicTextAnswerRequestDTO;
    private CheckClassicAnswerResultDTO checkClassicAnswerResultDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        AnswerForPlayDTO answerForPlayDTO = new AnswerForPlayDTO(1L, "Réponse");

        questionForPlayDTO = new QuestionForPlayDTO(
                1L,
                "EXPERT",
                "Theme",
                "Category",
                "Classique",
                "Instructions",
                "Question",
                List.of(answerForPlayDTO, answerForPlayDTO)
        );

        classicChoiceAnswerRequestDTO = new ClassicChoiceAnswerRequestDTO(
                GameType.CLASSIC_CHOICE,
                1L,
                1L,
                1L,
                true,
                true,
                List.of(1L),
                10
        );

        classicTextAnswerRequestDTO = new ClassicTextAnswerRequestDTO(
                GameType.CLASSIC_TEXT,
                1L,
                1L,
                1L,
                true,
                true,
                List.of("Réponse"),
                10
        );

        checkClassicAnswerResultDTO = new CheckClassicAnswerResultDTO(
                true,
                true,
                10,
                "Bonne réponse !",
                "Explication de la réponse",
                List.of(answerForPlayDTO)
        );
    }


    @Nested
    @DisplayName("GET /api/quizzes/{id}/questions")
    class GetQuestionsTests {

        @Test
        @DisplayName("Succès - Obtenir les questions d'un quiz actif pour le jeu")
        public void getQuestionsForPlay() throws Exception {
            // Given
            long difficultyLevelId = 1L;
            when(gameService.findAllQuestionsByQuizIdForPlay(anyLong(), anyLong()))
                    .thenReturn(List.of(questionForPlayDTO));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID + ApiRoutes.Questions.STRING, endpointId)
                            .param("difficultyLevelId", String.valueOf(difficultyLevelId)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].text").value(questionForPlayDTO.text()));
        }

        @Test
        @DisplayName("Erreur - Obtenir les questions d'un quiz actif pour le jeu - Quiz non trouvé")
        public void getQuestionsForPlay_NotFound() throws Exception {
            // Given
            long difficultyLevelId = 1L;
            when(gameService.findAllQuestionsByQuizIdForPlay(anyLong(), anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID + ApiRoutes.Questions.STRING, endpointId)
                            .param("difficultyLevelId", String.valueOf(difficultyLevelId)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès - Vérifier la réponse à une question (choix)")
        public void checkAnswer() throws Exception {
            // Given
            when(gameService.checkAnswer(any(QuestionAnswerRequestDTO.class)))
                    .thenReturn(checkClassicAnswerResultDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Others.CHECK_ANSWER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicChoiceAnswerRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(checkClassicAnswerResultDTO)));
        }

        @Test
        @DisplayName("Erreur - Vérifier la réponse à une question (choix) - Question non trouvée")
        public void checkAnswer_NotFound() throws Exception {
            // Given
            when(gameService.checkAnswer(any(QuestionAnswerRequestDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Others.CHECK_ANSWER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicChoiceAnswerRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Succès - Vérifier la réponse à une question (text)")
        public void checkAnswerText() throws Exception {
            // Given
            when(gameService.checkAnswer(any(QuestionAnswerRequestDTO.class)))
                    .thenReturn(checkClassicAnswerResultDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Others.CHECK_ANSWER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicTextAnswerRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(checkClassicAnswerResultDTO)));
        }

        @Test
        @DisplayName("Erreur - Vérifier la réponse à une question (text) - Question non trouvée")
        public void checkAnswerText_NotFound() throws Exception {
            // Given
            when(gameService.checkAnswer(any(QuestionAnswerRequestDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Others.CHECK_ANSWER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicTextAnswerRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

    }
}
