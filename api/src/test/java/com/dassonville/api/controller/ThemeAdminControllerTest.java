package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.ThemeUpsertDTO;
import com.dassonville.api.dto.response.ThemeAdminDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.ThemeService;
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



    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);

        themeAdminDTO = new ThemeAdminDTO(
                1L,
                "Informatique",
                "Description",
                false,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                List.of());

        themeUpsertDTO = new ThemeUpsertDTO("Informatique", "Description");
    }


    @Nested
    @DisplayName("GET - Liste")
    class GetListTests {

        @Test
        @DisplayName("Succès - Récupérer la liste des thèmes en détail")
        public void getAllThemes_shouldReturn200() throws Exception {
            // Given
            when(themeService.getAllDetails())
                    .thenReturn(List.of(themeAdminDTO));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_THEMES + ApiRoutes.DETAILS))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            objectMapper.writeValueAsString(
                                    List.of(themeAdminDTO)
                            )
                    ));
        }

        @Test
        @DisplayName("Succès - Récupérer la liste des thèmes avec ID et nom")
        public void getAllThemes_shouldReturn200WithIdAndName() throws Exception {
            // Given
            TestIdAndNameProjection testIdAndNameProjection = new TestIdAndNameProjection();

            when(themeService.getAll())
                    .thenReturn(List.of(testIdAndNameProjection));

            // When & Then
            mockMvc.perform(get(ApiRoutes.Themes.ADMIN_THEMES))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            objectMapper.writeValueAsString(
                                    List.of(testIdAndNameProjection)
                            )
                    ));
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
                    .andExpect(content().json(objectMapper.writeValueAsString(themeAdminDTO)));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
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
                    .andExpect(content().json(objectMapper.writeValueAsString(themeAdminDTO)));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidThemeUpsertCases")
        @DisplayName("Erreur - Données invalides")
        public void createTheme_shouldReturn400(
                String name, String description,
                String expectedNameMessage, String expectedDescriptionMessage
        ) throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO(name, description);

            // When & Then
            mockMvc.perform(post(ApiRoutes.Themes.ADMIN_THEMES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
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
        public void updateTheme_shouldReturn200() throws Exception {
            // Given
            when(themeService.update(anyLong(), any(ThemeUpsertDTO.class)))
                    .thenReturn(themeAdminDTO);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(themeAdminDTO)));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST.getMessage()));
        }

        @ParameterizedTest
        @MethodSource("com.dassonville.api.util.TestValidationProvider#invalidThemeUpsertCases")
        @DisplayName("Erreur - Données invalides")
        public void updateTheme_shouldReturn400(
                String name, String description,
                String expectedNameMessage, String expectedDescriptionMessage
        ) throws Exception {
            // Given
            themeUpsertDTO = new ThemeUpsertDTO(name, description);

            // When & Then
            mockMvc.perform(put(ApiRoutes.Themes.ADMIN_BY_ID, endpointId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(themeUpsertDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                    .andExpect(jsonPath("$.errors.name").value(expectedNameMessage))
                    .andExpect(jsonPath("$.errors.description").value(expectedDescriptionMessage));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("Erreur - Action non autorisée")
        public void deleteTheme_shouldReturn409() throws Exception {
            // Given
            doThrow(new ActionNotAllowedException()).when(themeService).delete(anyLong());

            // When & Then
            mockMvc.perform(delete(ApiRoutes.Themes.ADMIN_BY_ID, endpointId))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value(ErrorCode.ACTION_NOT_ALLOWED.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.ACTION_NOT_ALLOWED.getMessage()));
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
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }

    }

}
