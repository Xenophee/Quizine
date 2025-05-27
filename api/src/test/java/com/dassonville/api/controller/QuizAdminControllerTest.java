package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.QuizAdminDTO;
import com.dassonville.api.dto.QuizAdminDetailsDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizAdminController.class)
@Import(ValidCategoryForThemeValidator.class)
@DisplayName("IT - ADMIN Controller : Quiz")
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
    private QuizAdminDTO quizAdminDTO;
    private QuizAdminDetailsDTO quizAdminDetailsDTO;
    private QuizUpsertDTO quizUpsertDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        quizAdminDTO = new QuizAdminDTO(1L, "Test Quiz", (byte) 15, true, LocalDateTime.now(), null, null, null);
        quizAdminDetailsDTO = new QuizAdminDetailsDTO(1L, "Test Quiz", null, null, null, 9L, 1L, List.of());
        quizUpsertDTO = new QuizUpsertDTO("Test Quiz", 9L, 1L);

        when(themeRepository.existsById(anyLong()))
                .thenReturn(true);
        when(categoryRepository.existsByIdAndThemeId(anyLong(), anyLong()))
                .thenReturn(true);
    }


    @Nested
    @DisplayName("GET")
    class GetTests {

        @Test
        @DisplayName("Succès - Récupérer la liste des quiz par thème")
        public void getQuizzesByTheme_shouldReturn200() throws Exception {
            // Given
            Page<QuizAdminDTO> quizPage = new PageImpl<>(List.of(quizAdminDTO));

            when(quizService.findAllByThemeIdForAdmin(anyLong(), any(Boolean.class), any(Pageable.class)))
                    .thenReturn(quizPage);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .param("themeId", "9")
                            .param("visible", "true")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "title"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.quizAdminDTOList[0].title").value(quizAdminDTO.title()));
        }

        @Test
        @DisplayName("Erreur - Récupérer la liste des quiz par thème - Thème non trouvé")
        public void getQuizzesByTheme_shouldReturn404() throws Exception {
            // Given
            when(quizService.findAllByThemeIdForAdmin(anyLong(), any(), any(Pageable.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .param("themeId", "999")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "title"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Succès - Récupérer un quiz par ID")
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
        @DisplayName("Erreur - Quiz non trouvé")
        public void getQuizById_shouldReturn404() throws Exception {
            // Given
            when(quizService.findByIdForAdmin(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
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
        @DisplayName("Erreur - Quiz avec titre déjà existant")
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
        @DisplayName("Erreur - Données invalides")
        public void createInvalidQuiz_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", null, null);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Quizzes.ADMIN_QUIZZES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.title").exists())
                    .andExpect(jsonPath("$.themeId").exists());
        }

        @Test
        @DisplayName("Erreur - Catégorie invalide pour le thème")
        public void createInvalidQuizWithWrongCategoryForTheme_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", 1L, 1L);
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
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
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
        @DisplayName("Erreur - Quiz avec titre déjà existant")
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
        @DisplayName("Erreur - Données invalides")
        public void updateInvalidQuiz_shouldReturn400() throws Exception {
            // Given
            quizUpsertDTO = new QuizUpsertDTO("", null, null);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(quizUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.title").exists())
                    .andExpect(jsonPath("$.themeId").exists());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
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
    @DisplayName("DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        public void deleteQuiz_shouldReturn204() throws Exception {
            // Given
            doNothing().when(quizService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Quizzes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
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
    @DisplayName("PATCH")
    class PatchTests {

        @Test
        @DisplayName("Succès")
        public void updateVisibilityQuiz_shouldReturn204() throws Exception {
            // Given
            doNothing().when(quizService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Quizzes.ADMIN_QUIZZES + ApiRoutes.VISIBILITY, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        public void updateVisibilityQuiz_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(quizService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Quizzes.ADMIN_QUIZZES + ApiRoutes.VISIBILITY, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
