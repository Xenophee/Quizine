package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.AnswerService;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnswerAdminController.class)
@DisplayName("IT - QuizAdminController")
public class AnswerAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AnswerService answerService;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private AnswerAdminDTO answerAdminDTO;
    private AnswerUpsertDTO answerUpsertDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        answerAdminDTO = new AnswerAdminDTO(1L, "Test", true, null, null, null);
        answerUpsertDTO = new AnswerUpsertDTO("Test", true);
    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer une réponse")
        void createAnswer_shouldReturn201() throws Exception {
            // Given
            when(answerService.create(anyLong(), any(AnswerUpsertDTO.class)))
                    .thenReturn(answerAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Answers.STRING + "/" + answerAdminDTO.id())))
                    .andExpect(jsonPath("$.text").value(answerAdminDTO.text()));
        }

        @Test
        @DisplayName("Créer une réponse avec un texte déjà existant pour la question")
        void createAnswer_shouldReturn409() throws Exception {
            // Given
            when(answerService.create(anyLong(), any(AnswerUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Créer une réponse avec des données invalides")
        void createAnswer_shouldReturn400() throws Exception {
            // Given
            answerUpsertDTO = new AnswerUpsertDTO("", true);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.text").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Mettre à jour une réponse")
        void updateAnswer_shouldReturn200() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(AnswerUpsertDTO.class)))
                    .thenReturn(answerAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value(answerAdminDTO.text()));
        }

        @Test
        @DisplayName("Mettre à jour une réponse avec un texte déjà existant pour la question")
        void updateAnswer_shouldReturn409() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(AnswerUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Mettre à jour une réponse avec des données invalides")
        void updateAnswer_shouldReturn400() throws Exception {
            // Given
            answerUpsertDTO = new AnswerUpsertDTO("", true);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.text").exists());
        }

        @Test
        @DisplayName("Mettre à jour une réponse inexistante")
        void updateAnswer_shouldReturn404() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(AnswerUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(answerUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer une réponse")
        void deleteAnswer_shouldReturn204() throws Exception {
            // Given
            doNothing().when(answerService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Answers.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Supprimer une réponse inexistante")
        void deleteAnswer_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(answerService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Answers.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PATCH")
    class PatchTests {

        @Test
        @DisplayName("Activer / désactiver une réponse")
        void toggleVisibility_shouldReturn204() throws Exception {
            // Given
            doNothing().when(answerService).toggleVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Activer / désactiver une réponse inexistante")
        void toggleVisibility_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(answerService).toggleVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
