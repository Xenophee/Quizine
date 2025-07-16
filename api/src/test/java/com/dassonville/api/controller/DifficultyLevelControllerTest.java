package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.response.DifficultyLevelPublicDTO;
import com.dassonville.api.service.DifficultyLevelService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DifficultyLevelController.class)
@DisplayName("IT - PUBLIC Controller : Niveaux de difficulté")
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
        difficultyLevelPublicDTO = new DifficultyLevelPublicDTO(1L, "Débutant", "EZ", "Une description", false);
    }


    @Test
    @DisplayName("Récupérer tous les niveaux de difficulté actifs")
    public void getAllActiveDifficultyLevels_shouldReturn200() throws Exception {
        // Given
        when(difficultyLevelService.getAllActiveDifficultyLevels(anyLong()))
                .thenReturn(List.of(difficultyLevelPublicDTO));

        // When & Then
        mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID + ApiRoutes.DifficultyLevels.STRING, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                List.of(difficultyLevelPublicDTO)
                        )
                ));
    }
}
