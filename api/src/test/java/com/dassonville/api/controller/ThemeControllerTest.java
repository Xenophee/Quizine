package com.dassonville.api.controller;


import com.dassonville.api.dto.ThemeDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ThemeController.class)
@DisplayName("Tests d'intégration de la classe ThemeController")
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ThemeService themeService;

    private long endpointId;
    private ThemeDTO themeDTO;
    private ThemeUpsertDTO themeUpsertDTO;

    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        themeDTO = new ThemeDTO(1L, "Informatique", "", null, null, null);
        themeUpsertDTO = new ThemeUpsertDTO("Informatique", "");
    }


    @Nested
    @DisplayName("Tests pour la méthode GET")
    class GetTests {

        @Test
        @DisplayName("Récupérer tous les thèmes")
        public void getAllThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllThemes())
                    .thenReturn(List.of(themeDTO));

            // When & Then
            mockMvc.perform(get("/api/themes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is(themeDTO.name())));
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void getAllActiveThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllActiveThemes())
                    .thenReturn(List.of(themeDTO));

            // When & Then
            mockMvc.perform(get("/api/themes/active"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is(themeDTO.name())));
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void getThemeById_shouldReturn200() throws Exception {
            // Given
            when(themeService.findById(anyLong()))
                    .thenReturn(themeDTO);

            // When & Then
            mockMvc.perform(get("/api/themes/{id}", endpointId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(themeDTO.name())));
        }

        @Test
        @DisplayName("Récupérer un thème inexistant par son ID")
        public void getThemeById_shouldReturn404() throws Exception {
            // Given
            when(themeService.findById(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get("/api/themes/{id}", endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer un thème")
        public void createTheme_shouldReturn201() throws Exception {
            // Given
            when(themeService.create(any(ThemeUpsertDTO.class)))
                    .thenReturn(themeDTO);

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString("/themes/" + themeDTO.id())))
                    .andExpect(jsonPath("$.name", is(themeDTO.name())));
        }

        @Test
        @DisplayName("Créer un thème avec un nom déjà existant")
        public void createTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.create(any(ThemeUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Créer un thème avec un nom vide")
        public void createTheme_shouldReturn400() throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO("", "");

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").exists());
        }

    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Modifier un thème")
        public void updateTheme_shouldReturn200() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenReturn(themeDTO);

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(themeDTO.name())));
        }

        @Test
        @DisplayName("Modifier un thème avec un nom déjà existant")
        public void updateTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenThrow(new AlreadyExistException());

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("Modifier un thème avec un nom vide")
        public void updateTheme_shouldReturn400() throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO("", "");

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Modifier un thème inexistant")
        public void updateTheme_shouldReturn404() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }


    @Nested
    @DisplayName("Tests pour la méthode DELETE")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer un thème")
        public void deleteTheme_shouldReturn204() throws Exception {
            // Given
            // When & Then
            mockMvc.perform(delete("/api/themes/{id}", endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Supprimer un thème inexistant")
        public void deleteTheme_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(themeService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete("/api/themes/{id}", endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }


    @Nested
    @DisplayName("Tests pour les méthodes PATCH")
    class PatchTests {

        @Test
        @DisplayName("Désactiver un thème")
        public void disableTheme_shouldReturn204() throws Exception {
            // Given
            doNothing().when(themeService).toggleDisable(anyLong());
            // When & Then
            mockMvc.perform(patch("/api/themes/{id}/toggle-disable", endpointId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Désactiver un thème inexistant")
        public void disableTheme_shouldReturn404() throws Exception {
            // Given
            doThrow(new NotFoundException()).when(themeService).toggleDisable(anyLong());
            // When & Then
            mockMvc.perform(patch("/api/themes/{id}/toggle-disable", endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists());
        }

    }

}
