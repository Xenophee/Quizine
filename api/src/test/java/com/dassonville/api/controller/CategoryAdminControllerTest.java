package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.CategoryUpsertDTO;
import com.dassonville.api.dto.response.CategoryAdminDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.CategoryService;
import com.dassonville.api.util.TestIdAndNameProjection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CategoryAdminController.class)
@DisplayName("IT - ADMIN Controller : Catégorie")
public class CategoryAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private CategoryUpsertDTO categoryUpsertDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;

        booleanRequestDTO = new BooleanRequestDTO(true);

        categoryAdminDTO = new CategoryAdminDTO(
                1L,
                "Un nom",
                "Une description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                6);

        categoryUpsertDTO = new CategoryUpsertDTO("Un nom", "Une description");
    }


    @Nested
    @DisplayName("GET")
    class GetTests {

        @Test
        @DisplayName("Succès - Récupérer la liste des catégories selon un thème")
        public void getCategoriesByTheme_shouldReturn200() throws Exception {
            // Given
            TestIdAndNameProjection testIdAndNameProjection = new TestIdAndNameProjection();

            when(categoryService.findAllByTheme(anyLong()))
                    .thenReturn(List.of(testIdAndNameProjection));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID + ApiRoutes.Categories.STRING, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            objectMapper.writeValueAsString(
                                    List.of(testIdAndNameProjection)
                            )
                    ));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void getCategoriesByTheme_shouldReturn404() throws Exception {
            // Given
            when(categoryService.findAllByTheme(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID + ApiRoutes.Categories.STRING, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Succès - Obtenir une catégorie par son ID")
        public void getCategoryById_shouldReturn200() throws Exception {
            // Given
            when(categoryService.findById(anyLong()))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(categoryAdminDTO)));
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void getCategoryById_shouldReturn404() throws Exception {
            // Given
            when(categoryService.findById(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        public void createCategory_shouldReturn201() throws Exception {
            // Given
            when(categoryService.create(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Categories.STRING + "/" + categoryAdminDTO.id())))
                    .andExpect(content().json(objectMapper.writeValueAsString(categoryAdminDTO)));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvée")
        public void createCategory_shouldReturn404() throws Exception {
            // Given
            when(categoryService.create(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        public void createCategory_shouldReturn409() throws Exception {
            // Given
            when(categoryService.create(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidCategoryUpsertCases")
        @DisplayName("Erreur - Données invalides")
        public void createCategory_shouldReturn400(
                String name, String description,
                String expectedNameMessage, String expectedDescriptionMessage
        ) throws Exception {
            // Given
            categoryUpsertDTO = new CategoryUpsertDTO(name, description);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_QUESTIONS_POST, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andExpect(jsonPath("$.errors.name").value(expectedNameMessage))
                    .andExpect(jsonPath("$.errors.description").value(expectedDescriptionMessage));
        }
    }


    @Nested
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        public void updateCategory_shouldReturn200() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(categoryAdminDTO)));
        }

        @Test
        @DisplayName("Erreur - Catégorie déjà existante")
        public void updateCategory_shouldReturn409() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidCategoryUpsertCases")
        @DisplayName("Erreur - Données invalides")
        public void updateCategory_shouldReturn400(
                String name, String description,
                String expectedNameMessage, String expectedDescriptionMessage
        ) throws Exception {
            // Given
            categoryUpsertDTO = new CategoryUpsertDTO(name, description);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andExpect(jsonPath("$.errors.name").value(expectedNameMessage))
                    .andExpect(jsonPath("$.errors.description").value(expectedDescriptionMessage));
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void updateCategory_shouldReturn404() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
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
        public void deleteCategory_shouldReturn204() throws Exception {
            // Given
            doNothing().when(categoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void deleteCategory_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(categoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
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
        public void updateVisibilityCategory_shouldReturn204() throws Exception {
            // Given
            doNothing().when(categoryService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Categories.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Catégorie non trouvée")
        public void updateVisibilityCategory_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(categoryService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Categories.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }
    }
}
