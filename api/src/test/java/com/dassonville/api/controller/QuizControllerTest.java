package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.response.QuizPublicDTO;
import com.dassonville.api.dto.response.QuizPublicDetailsDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.service.QuestionService;
import com.dassonville.api.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
@DisplayName("IT - PUBLIC Controller : Quiz")
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuizService quizService;
    @MockitoBean
    private QuestionService questionService;


    private long endpointId;
    private QuizPublicDTO quizPublicDTO;
    private QuizPublicDetailsDTO quizDetailsDTO;


    @BeforeEach
    public void setUp() {
        endpointId = 1L;

        quizPublicDTO = new QuizPublicDTO(
                1L,
                "Test Quiz",
                "Classique",
                "ACCESSIBLE",
                true,
                5,
                "Category",
                "Theme"
        );

        quizDetailsDTO = new QuizPublicDetailsDTO(
                1L,
                "Test Quiz",
                "Description",
                "Classique",
                "ACCESSIBLE",
                true,
                5,
                "Category",
                "Theme"
        );
    }


    @Nested
    @DisplayName("GET")
    class GetTests {

        @Test
        @DisplayName("Succès - Obtenir la liste des quiz actifs selon des thèmes (avec pagination)")
        public void getQuizzes() throws Exception {
            // Given
            List<Long> themeIds = List.of(1L, 2L, 3L);
            Page<QuizPublicDTO> quizPage = new PageImpl<>(List.of(quizPublicDTO));

            when(quizService.findAllByThemeIdsForUser(eq(themeIds), any(Pageable.class)))
                    .thenReturn(quizPage);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BASE)
                            .param("themeIds", "1", "2", "3")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "createdAt,desc"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].id").value(quizPublicDTO.id()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].title").value(quizPublicDTO.title()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].quizType").value(quizPublicDTO.quizType()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].masteryLevel").value(quizPublicDTO.masteryLevel()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].isNew").value(quizPublicDTO.isNew()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].numberOfQuestions").value(quizPublicDTO.numberOfQuestions()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].theme").value(quizPublicDTO.theme()))
                    .andExpect(jsonPath("$._embedded.quizPublicDTOList[0].category").value(quizPublicDTO.category()));
        }

        @Test
        @DisplayName("Succès - Obtenir les détails d'un quiz actif")
        public void getQuizDetails() throws Exception {
            // Given
            when(quizService.findByIdForUser(anyLong()))
                    .thenReturn(quizDetailsDTO);

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID, endpointId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(quizDetailsDTO)));
        }

        @Test
        @DisplayName("Erreur - Obtenir les détails d'un quiz actif - Quiz non trouvé")
        public void getQuizDetails_NotFound() throws Exception {
            // Given
            when(quizService.findByIdForUser(anyLong()))
                    .thenThrow(new NotFoundException());

            // When & Then
            mockMvc.perform(get(ApiRoutes.Quizzes.BY_ID, endpointId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND.getMessage()));
        }
    }
}
