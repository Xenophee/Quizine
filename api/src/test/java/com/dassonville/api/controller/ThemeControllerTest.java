package com.dassonville.api.controller;


import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.MismatchedIdException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Theme;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme();
        theme.setId(1);
        theme.setName("Informatique");
        theme.setDescription("");
    }


    @Nested
    @DisplayName("Tests pour la méthode GET")
    class GetTests {

        @Test
        @DisplayName("Récupérer tous les thèmes")
        public void getAllThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllThemes())
                    .thenReturn(List.of(theme));

            // When & Then
            mockMvc.perform(get("/api/themes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is(theme.getName())));
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void getAllActiveThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllActiveThemes())
                    .thenReturn(List.of(theme));

            // When & Then
            mockMvc.perform(get("/api/themes/active"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is(theme.getName())));
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void getThemeById_shouldReturn200() throws Exception {
            // Given
            when(themeService.findById(any(Long.class)))
                    .thenReturn(theme);

            // When & Then
            mockMvc.perform(get("/api/themes/{id}", theme.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(theme.getName())));
        }

        @Test
        @DisplayName("Récupérer un thème inexistant par son ID")
        public void getThemeById_shouldReturn404() throws Exception {
            // Given
            when(themeService.findById(any(Long.class)))
                    .thenThrow(NotFoundException.class);

            // When & Then
            mockMvc.perform(get("/api/themes/{id}", theme.getId()))
                    .andExpect(status().isNotFound());
        }

    }


    @Nested
    @DisplayName("Tests pour la méthode POST")
    class PostTests {

        @Test
        @DisplayName("Créer un thème")
        public void createTheme_shouldReturn201() throws Exception {
            // Given
            when(themeService.create(any(Theme.class)))
                    .thenReturn(theme);

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString("/themes/" + theme.getId())))
                    .andExpect(jsonPath("$.name", is(theme.getName())));
        }

        @Test
        @DisplayName("Créer un thème avec un nom déjà existant")
        public void createTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.create(any(Theme.class)))
                    .thenThrow(AlreadyExistException.class);

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Créer un thème avec un nom vide")
        public void createTheme_shouldReturn400() throws Exception {
            // Given
            theme.setName("");

            // When & Then
            mockMvc.perform(post("/api/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isBadRequest());
        }

    }


    @Nested
    @DisplayName("Tests pour la méthode PUT")
    class PutTests {

        @Test
        @DisplayName("Modifier un thème")
        public void updateTheme_shouldReturn200() throws Exception {
            // Given
            when(themeService.update(any(Long.class), any(Theme.class)))
                    .thenReturn(theme);

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", theme.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(theme.getName())));
        }

        @Test
        @DisplayName("Modifier un thème avec un nom déjà existant")
        public void updateTheme_shouldReturn409() throws Exception {
            // Given
            when(themeService.update(any(Long.class), any(Theme.class)))
                    .thenThrow(AlreadyExistException.class);

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", theme.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Modifier un thème avec un nom vide")
        public void updateTheme_shouldReturn400_invalidData() throws Exception {
            // Given
            theme.setName("");

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", theme.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Modifier un thème avec un ID discordant")
        public void updateTheme_shouldReturn400_mismatchedId() throws Exception {
            // Given
            when(themeService.update(any(Long.class), any(Theme.class)))
                    .thenThrow(MismatchedIdException.class);

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", theme.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Modifier un thème inexistant")
        public void updateTheme_shouldReturn404() throws Exception {
            // Given
            when(themeService.update(any(Long.class), any(Theme.class)))
                    .thenThrow(NotFoundException.class);

            // When & Then
            mockMvc.perform(put("/api/themes/{id}", theme.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(theme)))
                    .andExpect(status().isNotFound());
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
            mockMvc.perform(delete("/api/themes/{id}", theme.getId()))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Supprimer un thème inexistant")
        public void deleteTheme_shouldReturn404() throws Exception {
            // Given
            doThrow(NotFoundException.class).when(themeService).delete(any(Long.class));

            // When & Then
            mockMvc.perform(delete("/api/themes/{id}", theme.getId()))
                    .andExpect(status().isNotFound());
        }

    }

}
