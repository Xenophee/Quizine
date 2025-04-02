package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.DifficultyLevelService;
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

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(DifficultyLevelAdminController.class)
@DisplayName("IT - DifficultyLevelAdminController")
public class DifficultyLevelAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DifficultyLevelService difficultyLevelService;

    private long endpointId;
    private ToggleDisableRequestDTO toggleDisableRequestDTO;
    private DifficultyLevelUpsertDTO difficultyLevelUpsertDTO;
    private DifficultyLevelAdminDTO difficultyLevelAdminDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        toggleDisableRequestDTO = new ToggleDisableRequestDTO(true);
        difficultyLevelAdminDTO = new DifficultyLevelAdminDTO(1L, "Facile", (byte) 2, (short) 0, 5, LocalDate.now(), null, null);
        difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO("Facile", (byte) 2, (short) 0, 5);
    }


    @Nested
    @DisplayName("Tests pour la méthode GET")
    class GetTests {

        @Test
        @DisplayName("Récupérer tous les niveaux de difficulté")
        public void getAllDifficultyLevels_shouldReturn200() throws Exception {
            // Given
            when(difficultyLevelService.getAllDifficultyLevels())
                    .thenReturn(List.of(difficultyLevelAdminDTO));

            // When & Then
            mockMvc.perform(get(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name").value(difficultyLevelAdminDTO.name()));
        }

        @Test
        @DisplayName("Récupérer un niveau de difficulté par son ID")
        public void getDifficultyLevelById_shouldReturn200() throws Exception {
            // Given
            when(difficultyLevelService.findById(anyLong()))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(difficultyLevelAdminDTO.name())));
        }

        @Test
        @DisplayName("Récupérer un niveau de difficulté inexistant par son ID")
        public void getDifficultyLevelById_shouldReturn404() throws Exception {
            // Given
            when(difficultyLevelService.findById(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer un niveau de difficulté")
        public void createDifficultyLevel_shouldReturn201() throws Exception {
            // Given
            when(difficultyLevelService.create(any(DifficultyLevelUpsertDTO.class)))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is(difficultyLevelAdminDTO.name())));
        }

        @Test
        @DisplayName("Créer un niveau de difficulté avec des données invalides")
        public void createDifficultyLevel_shouldReturn400() throws Exception {
            // Given
            difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO("", (byte) 2, (short) 0, 5);

            // When & Then
            mockMvc.perform(post(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

        @Test
        @DisplayName("Créer un niveau de difficulté avec un nom déjà existant")
        public void createDifficultyLevel_shouldReturn409() throws Exception {
            // Given
            when(difficultyLevelService.create(difficultyLevelUpsertDTO))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Modifier un niveau de difficulté")
        public void updateDifficultyLevel_shouldReturn200() throws Exception {
            // Given
            when(difficultyLevelService.update(anyLong(), any(DifficultyLevelUpsertDTO.class)))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(difficultyLevelAdminDTO.name())));
        }

        @Test
        @DisplayName("Modifier un niveau de difficulté avec un nom déjà existant")
        public void updateDifficultyLevel_shouldReturn409() throws Exception {
            // Given
            when(difficultyLevelService.update(anyLong(), any(DifficultyLevelUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Modifier un niveau de difficulté avec des données invalides")
        public void updateDifficultyLevel_shouldReturn400() throws Exception {
            // Given
            difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO("", (byte) 2, (short) 0, 5);

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

        @Test
        @DisplayName("Modifier un niveau de difficulté inexistant")
        public void updateDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            when(difficultyLevelService.update(anyLong(), any(DifficultyLevelUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Désactiver un niveau de difficulté")
        public void deleteDifficultyLevel_shouldReturn200() throws Exception {
            // Given
            doNothing().when(difficultyLevelService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Désactiver un niveau de difficulté inexistant")
        public void deleteDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(difficultyLevelService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("Tests pour la méthode PATCH")
    class PatchTests {

        @Test
        @DisplayName("Activer / désactiver un niveau de difficulté")
        public void toggleDisableDifficultyLevel_shouldReturn204() throws Exception {
            // Given
            doNothing().when(difficultyLevelService).toggleDisable(anyLong(), any(ToggleDisableRequestDTO.class));

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toggleDisableRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Activer / désactiver un niveau de difficulté inexistant")
        public void toggleDisableDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(difficultyLevelService).toggleDisable(anyLong(), any(ToggleDisableRequestDTO.class));

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toggleDisableRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }
}
