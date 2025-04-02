package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.service.DifficultyLevelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DifficultyLevelController.class)
@DisplayName("IT - DifficultyLevelController")
public class DifficultyLevelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DifficultyLevelService difficultyLevelService;


    private DifficultyLevelPublicDTO difficultyLevelPublicDTO;

    @BeforeEach
    public void setUp() {
        difficultyLevelPublicDTO = new DifficultyLevelPublicDTO(1L, "Débutant", (byte) 2, (short) 0, 15, false);
    }


    @Test
    @DisplayName("Récupérer tous les niveaux de difficulté actifs")
    public void getAllActiveDifficultyLevels_shouldReturn200() throws Exception {
        // Given
        when(difficultyLevelService.getAllActiveDifficultyLevels())
                .thenReturn(List.of(difficultyLevelPublicDTO));

        // When & Then
        mockMvc.perform(get(ApiRoutes.DifficultyLevels.BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(difficultyLevelPublicDTO.name())));
    }
}
