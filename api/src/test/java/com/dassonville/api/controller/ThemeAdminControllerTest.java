package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.service.ThemeService;
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

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ThemeAdminController.class)
@DisplayName("IT - ADMIN Controller : Thème")
public class ThemeAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ThemeService themeService;

    private long endpointId;
    private BooleanRequestDTO booleanRequestDTO;
    private ThemeAdminDTO themeAdminDTO;
    private ThemeUpsertDTO themeUpsertDTO;
    private IdAndNameProjection idAndNameProjection;



    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);
        themeAdminDTO = new ThemeAdminDTO(1L, "Informatique", "", null, null, null, List.of());
        themeUpsertDTO = new ThemeUpsertDTO("Informatique", "");

        idAndNameProjection = new IdAndNameProjection() {
            @Override
            public Long getId() {
                return 1L;
            }
            @Override
            public String getName() {
                return "Informatique";
            }
        };
    }


    @Nested
    @DisplayName("GET - Liste")
    class GetListTests {

        @Test
        @DisplayName("Succès - Récupérer la liste des thèmes en détail")
        public void getAllThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllThemesDetails())
                    .thenReturn(List.of(themeAdminDTO));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_THEMES + ApiRoutes.DETAILS))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name").value(themeAdminDTO.name()));
        }

        @Test
        @DisplayName("Succès - Récupérer la liste des thèmes avec ID et nom")
        public void getAllThemes_shouldReturn200WithIdAndName() throws Exception {
            // Given
            when(themeService.getAllThemes())
                    .thenReturn(List.of(idAndNameProjection));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_THEMES))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name").value("Informatique"));
        }

        @Test
        @DisplayName("Succès - Récupérer la liste des catégories selon un thème")
        public void getCategoriesByTheme_shouldReturn200() throws Exception {
            // Given
            when(themeService.getCategoriesByTheme(anyLong()))
                    .thenReturn(List.of(idAndNameProjection));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID + ApiRoutes.Categories.STRING, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name").value(idAndNameProjection.getName()));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void getCategoriesByTheme_shouldReturn404() throws Exception {
            // Given
            when(themeService.getCategoriesByTheme(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID + ApiRoutes.Categories.STRING, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("GET - Par ID")
    class GetByIdTests {

        @Test
        @DisplayName("Succès - Récupérer un thème par son ID")
        public void getThemeById_shouldReturn200() throws Exception {
            // Given
            when(themeService.findByIdForAdmin(anyLong()))
                    .thenReturn(themeAdminDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(themeAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void getThemeById_shouldReturn404() throws Exception {
            // Given
            when(themeService.findByIdForAdmin(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }
    }


    @Nested
    @DisplayName("POST")
    class PostTests {

        @Test
        @DisplayName("Succès")
        public void createTheme_shouldReturn201() throws Exception {
            // Given
            when(themeService.create(any(ThemeUpsertDTO.class)))
                    .thenReturn(themeAdminDTO);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Themes.ADMIN_THEMES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString(ApiRoutes.Themes.STRING + "/" + themeAdminDTO.id())))
                    .andExpect(jsonPath("$.name").value(themeAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void createTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.create(any(ThemeUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post(ApiRoutes.Themes.ADMIN_THEMES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        public void createTheme_shouldReturn400() throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO("", "");

            // When & Then
            mockMvc.perform(post(ApiRoutes.Themes.ADMIN_THEMES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

    }


    @Nested
    @DisplayName("PUT")
    class PutTests {

        @Test
        @DisplayName("Succès")
        public void updateTheme_shouldReturn200() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenReturn(themeAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(themeAdminDTO.name()));
        }

        @Test
        @DisplayName("Erreur - Thème déjà existant")
        public void updateTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Données invalides")
        public void updateTheme_shouldReturn400() throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO("", "");

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void updateTheme_shouldReturn404() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }


    @Nested
    @DisplayName("DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Succès")
        public void deleteTheme_shouldReturn204() throws Exception {
            // Given
            doNothing().when(themeService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void deleteTheme_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(themeService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Erreur - Action non autorisée")
        public void deleteTheme_shouldReturn409() throws Exception {
            // Given
            doThrow(new ActionNotAllowedException()).when(themeService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

    }


    @Nested
    @DisplayName("PATCH")
    class PatchTests {

        @Test
        @DisplayName("Succès")
        public void updateVisibilityTheme_shouldReturn204() throws Exception {
            // Given
            doNothing().when(themeService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Themes.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        public void updateVisibilityTheme_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(themeService).updateVisibility(anyLong(), anyBoolean());

            // When & Then
            mockMvc.perform(patch(ApiRoutes.Themes.ADMIN_VISIBILITY_PATCH, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(booleanRequestDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }

}
