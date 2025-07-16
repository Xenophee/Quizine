package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.constant.AppConstants;
import com.dassonville.api.constant.RequestActionType;
import com.dassonville.api.dto.request.*;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.repository.GameRuleRepository;
import com.dassonville.api.service.QuestionService;
import com.dassonville.api.util.JsonCollect;
import com.dassonville.api.validation.validator.ValidMinAnswersPerQuestionValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private GameRuleRepository gameRuleRepository;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private QuestionAdminDTO questionAdminDTO;
    private ClassicQuestionInsertDTO classicQuestionInsertDTO;
    private ClassicQuestionUpdateDTO classicQuestionUpdateDTO;


    @BeforeEach
    void setUp() {

        endpointId = 1L;

        booleanRequestDTO = new BooleanRequestDTO(true);

        questionAdminDTO = new QuestionAdminDTO(1L,
                "Question",
                "Explication",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                List.of()
        );

        classicQuestionInsertDTO = new ClassicQuestionInsertDTO(
                RequestActionType.CLASSIC_INSERT,
                "Question avec le nombre de caractères requis",
                "Explication avec le nombre de caractères requis",
                List.of(
                        new ClassicAnswerUpsertDTO("Réponse", true),
                        new ClassicAnswerUpsertDTO("Réponse2", false)
                ));

        classicQuestionUpdateDTO = new ClassicQuestionUpdateDTO(
                RequestActionType.CLASSIC_UPDATE,
                "Question mise à jour avec le nombre de caractères requis",
                "Explication mise à jour avec le nombre de caractères requis"
        );

        when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(AppConstants.CLASSIC_TYPE))
                .thenReturn(Optional.of((byte) 2));
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        void createQuestion_shouldReturn201() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionUpsertDTO.class)))
                    .thenReturn(questionAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionInsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Questions.STRING + "/" + questionAdminDTO.id())))
                    .andExpect(content().json(objectMapper.writeValueAsString(questionAdminDTO)));
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvée")
        void createQuestion_shouldReturn404() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionInsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Texte de la question déjà existant")
        void createQuestion_shouldReturn409() throws Exception {
            // Given
            when(questionService.create(anyLong(), any(QuestionUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionInsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidQuestionInsertCases")
        @DisplayName("Erreur - Données invalides")
        public void createInvalidQuestion_shouldReturn400(
                ClassicQuestionInsertDTO classicQuestionInsertDTO,
                String... expectedErrors
        ) throws Exception {
            // Given
            when(gameRuleRepository.findMaxAnswerOptionsCountByQuestionTypeCode(AppConstants.CLASSIC_TYPE))
                    .thenReturn(Optional.of((byte) 2));

            // When & Then
            MvcResult mvcResult = mockMvc.perform(post(ApiRoutes.Questions.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionInsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andReturn();

            // Parse JSON errors map
            String json = mvcResult.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode errorsNode = root.path("errors");

            // Collect all string values recursively
            List<String> allErrors = new ArrayList<>();
            JsonCollect.collectAllStrings(errorsNode, allErrors);

            // Vérifie que chaque message attendu est présent
            for (String expectedError : expectedErrors) {
                assertThat(allErrors)
                        .as("Erreur attendue non trouvée : " + expectedError)
                        .contains(expectedError);
            }

            // Vérifie la taille
            assertThat(allErrors)
                    .as("Nombre d'erreurs inattendu")
                    .hasSize(expectedErrors.length);
        }
    }


    @Nested
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        void updateQuestion_shouldReturn200() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(ClassicQuestionUpdateDTO.class)))
                    .thenReturn(questionAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionUpdateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(questionAdminDTO)));
        }

        @Test
        @DisplayName("Erreur - Texte de la question déjà existant")
        void updateQuestion_shouldReturn409() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(QuestionUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionUpdateDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void updateQuestion_shouldReturn404() throws Exception {
            // Given
            when(questionService.update(anyLong(), any(ClassicQuestionUpdateDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionUpdateDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidQuestionUpdateCases")
        @DisplayName("Erreur - Données invalides")
        public void updateInvalidQuestion_shouldReturn400(
                ClassicQuestionUpdateDTO classicQuestionUpdateDTO,
                String... expectedErrors
        ) throws Exception {

            // When & Then
            var result = mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionUpdateDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()));

            // Vérifie le nombre d'erreurs en comptant les clés de l'objet
            result.andExpect(jsonPath("$.errors", Matchers.aMapWithSize(expectedErrors.length)));

            // Vérifie que chaque erreur attendue est présente dans le tableau des erreurs
            for (String expectedError : expectedErrors) {
                result.andExpect(jsonPath("$.errors.*", Matchers.hasItem(expectedError)));
            }
        }

        @Test
        @DisplayName("Erreur - Format JSON invalide")
        void updateQuestion_shouldReturn400_InvalidJson() throws Exception {
            // Given
            ClassicQuestionUpdateDTO classicQuestionUpdateDTO = new ClassicQuestionUpdateDTO(null, "A", "B");

            // When & Then
            mockMvc.perform(put(ApiRoutes.Questions.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(classicQuestionUpdateDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.DESERIALIZATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.DESERIALIZATION_ERROR.getMessage()));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }
    }
}
