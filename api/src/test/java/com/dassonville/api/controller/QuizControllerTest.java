package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.*;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.QuestionService;
import com.dassonville.api.service.QuizService;
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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
@DisplayName("IT - PUBLIC Controller : Quiz")
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuizService quizService;
    @MockitoBean
    private QuestionService questionService;


    private long endpointId;
    private QuizPublicDetailsDTO quizDetailsDTO;
    private QuestionForPlayDTO questionForPlayDTO;
    private CheckAnswerChoicesRequestDTO checkAnswerChoicesRequestDTO;
    private CheckAnswerTextsRequestDTO checkAnswerTextsRequestDTO;
    private CheckAnswerResultDTO checkAnswerResultDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        AnswerForPlayDTO answerForPlayDTO = new AnswerForPlayDTO(1L, "Réponse");

        quizDetailsDTO = new QuizPublicDetailsDTO(1L, "Test Quiz", true, 5, "Category", "Theme");

        questionForPlayDTO = new QuestionForPlayDTO(1L, "Question", List.of(answerForPlayDTO, answerForPlayDTO));

        checkAnswerChoicesRequestDTO = new CheckAnswerChoicesRequestDTO(List.of(1L));
        checkAnswerTextsRequestDTO = new CheckAnswerTextsRequestDTO(List.of("Réponse"));
        checkAnswerResultDTO = new CheckAnswerResultDTO(true, List.of(answerForPlayDTO));
    }


    @Nested
    @DisplayName("GET")
    class GetTests {

        @Test
        @DisplayName("Succès - Obtenir les détails d'un quiz actif")
        public void getQuizDetails() throws Exception {
            // Given
            when(quizService.findByIdForUser(anyLong()))
                    .thenReturn(quizDetailsDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(quizDetailsDTO.title()));
        }

        @Test
        @DisplayName("Erreur - Obtenir les détails d'un quiz actif - Quiz non trouvé")
        public void getQuizDetails_NotFound() throws Exception {
            // Given
            when(quizService.findByIdForUser(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Succès - Obtenir les questions d'un quiz actif pour le jeu")
        public void getQuestionsForPlay() throws Exception {
            // Given
            long difficultyLevelId = 1L;
            when(quizService.findAllQuestionsByQuizIdForPlay(anyLong(), anyLong()))
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
            when(quizService.findAllQuestionsByQuizIdForPlay(anyLong(), anyLong()))
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
            when(questionService.checkAnswerByChoice(anyLong(), anyLong(), anyList()))
                    .thenReturn(checkAnswerResultDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.BASE + ApiRoutes.Questions.CHECK_ANSWER_CHOICE, endpointId, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(checkAnswerChoicesRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.isCorrect").value(checkAnswerResultDTO.isCorrect()));
        }

        @Test
        @DisplayName("Erreur - Vérifier la réponse à une question (choix) - Question non trouvée")
        public void checkAnswer_NotFound() throws Exception {
            // Given
            when(questionService.checkAnswerByChoice(anyLong(), anyLong(), anyList()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.BASE + ApiRoutes.Questions.CHECK_ANSWER_CHOICE, endpointId, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(checkAnswerChoicesRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Succès - Vérifier la réponse à une question (text)")
        public void checkAnswerText() throws Exception {
            // Given
            when(questionService.checkAnswerByText(anyLong(), anyLong(), anyList()))
                    .thenReturn(checkAnswerResultDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.BASE + ApiRoutes.Questions.CHECK_ANSWER_TEXT, endpointId, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(checkAnswerTextsRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.isCorrect").value(checkAnswerResultDTO.isCorrect()));
        }

        @Test
        @DisplayName("Erreur - Vérifier la réponse à une question (text) - Question non trouvée")
        public void checkAnswerText_NotFound() throws Exception {
            // Given
            when(questionService.checkAnswerByText(anyLong(), anyLong(), anyList()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.BASE + ApiRoutes.Questions.CHECK_ANSWER_TEXT, endpointId, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(checkAnswerTextsRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }
}
