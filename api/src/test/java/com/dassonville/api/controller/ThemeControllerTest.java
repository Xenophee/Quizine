package com.dassonville.api.controller;

import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ThemeController.class)
@DisplayName("IT - PUBLIC Controller : Thème")
public class ThemeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ThemeService themeService;

    private long endpointId;
    private ThemePublicDTO themePublicDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;
        themePublicDTO = new ThemePublicDTO(1L, "Informatique", "", false);
    }


    @Test
    @DisplayName("Récupérer tous les thèmes actifs")
    public void getAllActiveThemes_shouldReturn200() throws Exception {
        // Given
        when(themeService.getAllActiveThemes())
                .thenReturn(List.of(themePublicDTO));

        // When & Then
        mockMvc.perform(get(ApiRoutes.Themes.BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(themePublicDTO.name()));
    }
}
