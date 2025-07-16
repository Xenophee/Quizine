package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
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
@DisplayName("IT - ADMIN Controller : Réponse")
public class ClassicAnswerAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AnswerService answerService;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private AnswerAdminDTO answerAdminDTO;
    private ClassicAnswerUpsertDTO classicAnswerUpsertDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        answerAdminDTO = new AnswerAdminDTO(1L, "Test", true, null, null, null);
        classicAnswerUpsertDTO = new ClassicAnswerUpsertDTO("Test", true);
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        void createAnswer_shouldReturn201() throws Exception {
            // Given
            when(answerService.create(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenReturn(answerAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Answers.STRING + "/" + answerAdminDTO.id())))
                    .andExpect(jsonPath("$.text").value(answerAdminDTO.text()))
                    .andExpect(jsonPath("$.isCorrect").value(answerAdminDTO.isCorrect()));
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void createAnswer_shouldReturn404() throws Exception {
            // Given
            when(answerService.create(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        void createAnswer_shouldReturn409() throws Exception {
            // Given
            when(answerService.create(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        void createAnswer_shouldReturn400() throws Exception {
            // Given
            classicAnswerUpsertDTO = new ClassicAnswerUpsertDTO("", null);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Answers.ADMIN_ANSWERS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andExpect(jsonPath("$.errors.text").value(FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK))
                    .andExpect(jsonPath("$.errors.isCorrect").value(FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL));
        }
    }


    @Nested
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        void updateAnswer_shouldReturn200() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenReturn(answerAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text").value(answerAdminDTO.text()))
                    .andExpect(jsonPath("$.isCorrect").value(answerAdminDTO.isCorrect()));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        void updateAnswer_shouldReturn409() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        void updateAnswer_shouldReturn400() throws Exception {
            // Given
            classicAnswerUpsertDTO = new ClassicAnswerUpsertDTO("", null);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andExpect(jsonPath("$.errors.text").value(FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK))
                    .andExpect(jsonPath("$.errors.isCorrect").value(FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL));
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void updateAnswer_shouldReturn404() throws Exception {
            // Given
            when(answerService.update(anyLong(), any(ClassicAnswerUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Answers.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicAnswerUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }
    }


    @Nested
    @DisplayName("DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        void deleteAnswer_shouldReturn204() throws Exception {
            // Given
            doNothing().when(answerService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Answers.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void deleteAnswer_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(answerService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Answers.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Action non autorisée")
        void deleteAnswer_shouldReturn409() throws Exception {
            // Given
            doThrow(new ActionNotAllowedException()).when(answerService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Answers.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ACTION_NOT_ALLOWED.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ACTION_NOT_ALLOWED.getMessage()));
        }
    }


    @Nested
    @DisplayName("PATCH")
    class PatchTests {

        @Test
        @DisplayName("Succès")
        void updateVisibility_shouldReturn204() throws Exception {
            // Given
            doNothing().when(answerService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void updateVisibility_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(answerService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Action non autorisée")
        void updateVisibility_shouldReturn409() throws Exception {
            // Given
            doThrow(new ActionNotAllowedException()).when(answerService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ACTION_NOT_ALLOWED.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ACTION_NOT_ALLOWED.getMessage()));
        }
    }
}
