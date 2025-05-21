package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.*;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.service.QuestionService;
import com.dassonville.api.validation.validator.ValidMinAnswersPerQuestionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionAdminController.class)
@Import(ValidMinAnswersPerQuestionValidator.class)
@DisplayName("IT - ADMIN Controller : Question")
public class QuestionAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuestionService questionService;
    @MockitoBean
    private DifficultyLevelRepository difficultyLevelRepository;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private QuestionAdminDTO questionAdminDTO;
    private QuestionInsertDTO questionInsertDTO;
    private QuestionUpdateDTO questionUpdateDTO;


    @BeforeEach
    void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        questionAdminDTO = new QuestionAdminDTO(1L, "Question", null, null, null, List.of());
        questionInsertDTO = new QuestionInsertDTO("Question", List.of(
                new AnswerUpsertDTO("Réponse", true),
                new AnswerUpsertDTO("Réponse2", false)
        ));
        questionUpdateDTO = new QuestionUpdateDTO("Question");

        when(difficultyLevelRepository.findReferenceLevelMaxAnswers()).thenReturn(Optional.of((byte) 2));
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        void createQuestion_shouldReturn201() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionInsertDTO.class)))
                    .thenReturn(questionAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Questions.STRING + "/" + questionAdminDTO.id())))
                    .andExpect(jsonPath("$.text").value(questionAdminDTO.text()));
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvée")
        void createQuestion_shouldReturn404() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionInsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Texte de la question déjà existant")
        void createQuestion_shouldReturn409() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionInsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides (null)")
        void createQuestion_shouldReturn400() throws Exception {
            // Given
            questionInsertDTO = new QuestionInsertDTO(null, null);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.text").exists())
                    .andExpect(jsonPath("$.answers").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides (pas assez de réponses)")
        void createQuestion_shouldReturn400_2() throws Exception {
            // Given
            questionInsertDTO = new QuestionInsertDTO("Question", List.of(new AnswerUpsertDTO("Réponse", true)));

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.answers").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides (pas de réponses correctes)")
        void createQuestion_shouldReturn400_3() throws Exception {
            // Given
            questionInsertDTO = new QuestionInsertDTO("Question",
                    List.of(new AnswerUpsertDTO("Réponse", false), new AnswerUpsertDTO("Réponse2", false)));

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.answers").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides (réponses sans texte)")
        void createQuestion_shouldReturn400_4() throws Exception {
            // Given
            questionInsertDTO = new QuestionInsertDTO("",
                    List.of(new AnswerUpsertDTO("", true), new AnswerUpsertDTO("", false)));

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.text").exists())
                    .andExpect(jsonPath("$.answers[0].text").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides (doublon de réponse)")
        void createQuestion_shouldReturn400_5() throws Exception {
            // Given
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 3));

            questionInsertDTO = new QuestionInsertDTO("Question",
                    List.of(
                            new AnswerUpsertDTO("Réponse", true),
                            new AnswerUpsertDTO("Réponse", false),
                            new AnswerUpsertDTO("Réponse2", false)
                    ));

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.answers").exists());
        }
    }


    @Nested
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        void updateQuestion_shouldReturn200() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(QuestionUpdateDTO.class)))
                    .thenReturn(questionAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionUpdateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value(questionAdminDTO.text()));
        }

        @Test
        @DisplayName("Erreur - Texte de la question déjà existant")
        void updateQuestion_shouldReturn409() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(QuestionUpdateDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionUpdateDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        void updateQuestion_shouldReturn400() throws Exception {
            // Given
            questionUpdateDTO = new QuestionUpdateDTO(null);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionUpdateDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.text").exists());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateQuestion_shouldReturn404() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(QuestionUpdateDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(questionUpdateDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        void deleteQuestion_shouldReturn204() throws Exception {
            // Given
            doNothing().when(questionService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Questions.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void deleteQuestion_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(questionService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Questions.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("PATCH")
    class PatchTests {

        @Test
        @DisplayName("Succès")
        void updateVisibilityQuestion_shouldReturn204() throws Exception {
            // Given
            doNothing().when(questionService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Questions.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateVisibilityQuestion_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(questionService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Questions.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
