package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizInactiveAdminDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.repository.CategoryRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.service.QuizService;
import com.dassonville.api.validation.validator.ValidCategoryForThemeValidator;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizAdminController.class)
@Import(ValidCategoryForThemeValidator.class)
@DisplayName("IT - QuizAdminController")
public class QuizAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuizService quizService;
    @MockitoBean
    private ThemeRepository themeRepository;
    @MockitoBean
    private CategoryRepository categoryRepository;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private QuizAdminDetailsDTO quizAdminDetailsDTO;
    private QuizInactiveAdminDTO quizInactiveAdminDTO;
    private QuizUpsertDTO quizUpsertDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        quizAdminDetailsDTO = new QuizAdminDetailsDTO(1L, "Test Quiz", false, null, null, null, 9L, 1L, List.of());
        quizInactiveAdminDTO = new QuizInactiveAdminDTO(1L, "Inactive Quiz", false, (byte) 9, null, null, null, null, null);
        quizUpsertDTO = new QuizUpsertDTO("Test Quiz", false, 9L, 1L);

        when(themeRepository.existsById(anyLong()))
                .thenReturn(true);
        when(categoryRepository.existsByIdAndThemeId(anyLong(), anyLong()))
                .thenReturn(true);
    }


    @Nested
    @DisplayName("Tests pour la méthode GET")
    class GetTests {

        @Test
        @DisplayName("Récupérer un quiz par ID")
        public void getQuizById_shouldReturn200() throws Exception {
            // Given
            when(quizService.findByIdForAdmin(anyLong()))
                    .thenReturn(quizAdminDetailsDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(quizAdminDetailsDTO.title()));
        }

        @Test
        @DisplayName("Récupérer un quiz inexistant par son ID")
        public void getQuizById_shouldReturn404() throws Exception {
            // Given
            when(quizService.findByIdForAdmin(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Récupérer tous les quiz inactifs")
        public void getAllInactiveQuizzes_shouldReturn200() throws Exception {
            // Given
            when(quizService.getAllInactiveQuiz())
                    .thenReturn(List.of(quizInactiveAdminDTO));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_QUIZZES + ApiRoutes.INACTIVE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].title").value(quizInactiveAdminDTO.title()));
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer un quiz")
        public void createQuiz_shouldReturn201() throws Exception {
            // Given
            when(quizService.create(any(QuizUpsertDTO.class)))
                    .thenReturn(quizAdminDetailsDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Quizzes.STRING + "/" + quizAdminDetailsDTO.id())))
                    .andExpect(jsonPath("$.title").value(quizAdminDetailsDTO.title()));
        }

        @Test
        @DisplayName("Créer un quiz avec un titre déjà existant")
        public void createQuiz_shouldReturn409() throws Exception {
            // Given
            when(quizService.create(any(QuizUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Créer un quiz avec des données invalides")
        public void createInvalidQuiz_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", false, null, null);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.title").exists())
                    .andExpect(jsonPath("$.themeId").exists());
        }

        @Test
        @DisplayName("Créer un quiz avec une correspondance de catégorie et de thème erronée")
        public void createInvalidQuizWithWrongCategoryForTheme_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", false, 1L, 1L);
            when(categoryRepository.existsByIdAndThemeId(anyLong(), anyLong()))
                    .thenReturn(false);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.title").exists())
                    .andExpect(jsonPath("$.categoryId").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Mettre à jour un quiz")
        public void updateQuiz_shouldReturn200() throws Exception {
            // Given
            when(quizService.update(anyLong(), any(QuizUpsertDTO.class)))
                    .thenReturn(quizAdminDetailsDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(quizAdminDetailsDTO.title()));
        }

        @Test
        @DisplayName("Mettre à jour un quiz avec un titre déjà existant")
        public void updateQuiz_shouldReturn409() throws Exception {
            // Given
            when(quizService.update(anyLong(), any(QuizUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Mettre à jour un quiz avec des données invalides")
        public void updateInvalidQuiz_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", false, null, null);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.title").exists())
                    .andExpect(jsonPath("$.themeId").exists());
        }

        @Test
        @DisplayName("Mettre à jour un quiz inexistant")
        public void updateQuiz_shouldReturn404() throws Exception {
            // Given
            when(quizService.update(anyLong(), any(QuizUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer un quiz")
        public void deleteQuiz_shouldReturn204() throws Exception {
            // Given
            doNothing().when(quizService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Supprimer un quiz inexistant")
        public void deleteQuiz_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(quizService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PATCH")
    class PatchTests {

        @Test
        @DisplayName("Désactiver un quiz")
        public void disableQuiz_shouldReturn204() throws Exception {
            // Given
            doNothing().when(quizService).toggleVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Quizzes.ADMIN_QUIZZES + ApiRoutes.VISIBILITY, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Désactiver un quiz inexistant")
        public void disableQuiz_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(quizService).toggleVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Quizzes.ADMIN_QUIZZES + ApiRoutes.VISIBILITY, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
