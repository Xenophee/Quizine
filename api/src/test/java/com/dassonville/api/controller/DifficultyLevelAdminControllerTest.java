package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ReorderRequestDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.repository.DifficultyLevelRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(DifficultyLevelAdminController.class)
@DisplayName("IT - ADMIN Controller : Niveaux de difficulté")
public class DifficultyLevelAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DifficultyLevelService difficultyLevelService;
    @MockitoBean
    private DifficultyLevelRepository difficultyLevelRepository;


    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private DifficultyLevelUpsertDTO difficultyLevelUpsertDTO;
    private DifficultyLevelAdminDTO difficultyLevelAdminDTO;
    private ReorderRequestDTO reorderRequestDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        difficultyLevelAdminDTO = new DifficultyLevelAdminDTO(1L, "Facile", (byte) 2, (short) 0, 5,
                false, (byte) 5, LocalDateTime.now(), null, null);
        difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO("Facile", (byte) 2, (short) 0, 5);
        reorderRequestDTO = new ReorderRequestDTO(List.of(1L, 2L, 3L));

        when(difficultyLevelRepository.countBy()).thenReturn(3);
    }


    @Nested
    @DisplayName("GET")
    class GetTests {

        @Test
        @DisplayName("Succès - Récupérer tous les niveaux de difficulté")
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
        @DisplayName("Succès - Récupérer un niveau de difficulté par son ID")
        public void getDifficultyLevelById_shouldReturn200() throws Exception {
            // Given
            when(difficultyLevelService.findById(anyLong()))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(difficultyLevelAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
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
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        public void createDifficultyLevel_shouldReturn201() throws Exception {
            // Given
            when(difficultyLevelService.create(any(DifficultyLevelUpsertDTO.class)))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value(difficultyLevelAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        public void createDifficultyLevel_shouldReturn400() throws Exception {
            // Given
            difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO("", null, (short) -10, 0);

            // When & Then
            mockMvc.perform(post(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists())
                    .andExpect(jsonPath("$.maxAnswers").exists())
                    .andExpect(jsonPath("$.timerSeconds").exists())
                    .andExpect(jsonPath("$.pointsPerQuestion").exists());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté déjà existant")
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
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        public void updateDifficultyLevel_shouldReturn200() throws Exception {
            // Given
            when(difficultyLevelService.update(anyLong(), any(DifficultyLevelUpsertDTO.class)))
                    .thenReturn(difficultyLevelAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(difficultyLevelAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté déjà existant")
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
        @DisplayName("Erreur - Données invalides")
        public void updateDifficultyLevel_shouldReturn400() throws Exception {
            // Given
            difficultyLevelUpsertDTO = new DifficultyLevelUpsertDTO(" ", (byte) -2, null, null);

            // When & Then
            mockMvc.perform(put(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(difficultyLevelUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists())
                    .andExpect(jsonPath("$.maxAnswers").exists())
                    .andExpect(jsonPath("$.timerSeconds").exists())
                    .andExpect(jsonPath("$.pointsPerQuestion").exists());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
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
    @DisplayName("DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        public void deleteDifficultyLevel_shouldReturn200() throws Exception {
            // Given
            doNothing().when(difficultyLevelService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        public void deleteDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(difficultyLevelService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Action non autorisée")
        public void deleteDifficultyLevel_shouldReturn409() throws Exception {
            // Given
            doThrow(new ActionNotAllowedException()).when(difficultyLevelService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.DifficultyLevels.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("PATCH - Visibilité")
    class PatchVisibilityTests {

        @Test
        @DisplayName("Succès")
        public void updateVisibilityDifficultyLevel_shouldReturn204() throws Exception {
            // Given
            doNothing().when(difficultyLevelService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_VISIBILITY_PATCH, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Niveau de difficulté non trouvé")
        public void updateVisibilityDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(difficultyLevelService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_VISIBILITY_PATCH, endpointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("PATCH - Réordonner")
    class PatchReorderTests {

        @Test
        @DisplayName("Succès")
        public void reorderDifficultyLevel_shouldReturn204() throws Exception {
            // Given
            doNothing().when(difficultyLevelService).updateDisplayOrder(reorderRequestDTO.orderedIds());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS + ApiRoutes.REORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reorderRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Niveaux de difficulté non trouvés")
        public void reorderDifficultyLevel_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(difficultyLevelService).updateDisplayOrder(reorderRequestDTO.orderedIds());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS + ApiRoutes.REORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reorderRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Nombre de niveaux de difficulté incorrect")
        public void reorderDifficultyLevel_shouldReturn400() throws Exception {
            // Given
            reorderRequestDTO = new ReorderRequestDTO(List.of(1L, 2L));

            // When & Then
            mockMvc.perform(patch(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS + ApiRoutes.REORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reorderRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.orderedIds").exists());
        }
    }
}
