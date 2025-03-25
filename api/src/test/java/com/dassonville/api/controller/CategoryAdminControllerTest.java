package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.CategoryService;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CategoryAdminController.class)
@DisplayName("IT - CategoryController")
public class CategoryAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    private long endpointId;
    private ToggleDisableRequestDTO toggleDisableRequestDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private CategoryUpsertDTO categoryUpsertDTO;

    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);
        categoryAdminDTO = new CategoryAdminDTO(1L, "Code", "", null, null, null, 6);
        categoryUpsertDTO = new CategoryUpsertDTO("Code", "", 6);
    }


    @Nested
    @DisplayName("Tests pour la méthode GET")
    class GetTests {

        @Test
        @DisplayName("Obtenir une catégorie par son ID")
        public void getCategoryById_shouldReturn200() throws Exception {
            // Given
            when(categoryService.findById(anyLong()))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(categoryAdminDTO.name())));
        }

        @Test
        @DisplayName("Obtenir une catégorie inexistante par son ID")
        public void getCategoryById_shouldReturn404() throws Exception {
            // Given
            when(categoryService.findById(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer une catégorie")
        public void createCategory_shouldReturn201() throws Exception {
            // Given
            when(categoryService.create(any(CategoryUpsertDTO.class)))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_CATEGORIES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Categories.STRING + "/" + categoryAdminDTO.id())))
                    .andExpect(jsonPath("$.name", is(categoryAdminDTO.name())));
        }

        @Test
        @DisplayName("Créer une catégorie avec un nom déjà existant")
        public void createCategory_shouldReturn409() throws Exception {
            // Given
            when(categoryService.create(any(CategoryUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException("Une catégorie existe déjà avec le même nom."));

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_CATEGORIES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Créer une catégorie avec des données invalides")
        public void createCategory_shouldReturn400() throws Exception {
            // Given
            categoryUpsertDTO = new CategoryUpsertDTO("", "", 1);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Categories.ADMIN_CATEGORIES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Modifier une catégorie")
        public void updateCategory_shouldReturn200() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenReturn(categoryAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(categoryAdminDTO.name())));
        }

        @Test
        @DisplayName("Modifier une catégorie avec un nom déjà existant")
        public void updateCategory_shouldReturn409() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Modifier une catégorie avec des données invalides")
        public void updateCategory_shouldReturn400() throws Exception {
            // Given
            categoryUpsertDTO = new CategoryUpsertDTO("", "", 1);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

        @Test
        @DisplayName("Modifier une catégorie avec un ID inexistant")
        public void updateCategory_shouldReturn404() throws Exception {
            // Given
            when(categoryService.update(anyLong(), any(CategoryUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer une catégorie")
        public void deleteCategory_shouldReturn204() throws Exception {
            // Given
            doNothing().when(categoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Supprimer une catégorie avec un ID inexistant")
        public void deleteCategory_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(categoryService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Categories.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PATCH")
    class PatchTests {

        @Test
        @DisplayName("Désactiver une catégorie")
        public void toggleDisableCategory_shouldReturn204() throws Exception {
            // Given
            doNothing().when(categoryService).toggleDisable(anyLong(), any(ToggleDisableRequestDTO.class));

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(toggleDisableRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Désactiver une catégorie inexistante")
        public void toggleDisableCategory_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(categoryService).toggleDisable(anyLong(), any(ToggleDisableRequestDTO.class));

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Categories.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(toggleDisableRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
