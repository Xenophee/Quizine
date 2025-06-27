package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.QuestionAnswerRequestDTO;
import com.dassonville.api.dto.response.CheckAnswerResultDTO;
import com.dassonville.api.dto.response.QuestionForPlayDTO;
import com.dassonville.api.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "PUBLIC", description = "Endpoints accessibles publiquement")
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @Operation(summary = "Obtenir les questions d'un quiz actif pour le jeu",
            description = "Obtient les questions d'un quiz actif par son ID et le niveau de difficulté.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Les questions du quiz ont été trouvées."),
            @ApiResponse(responseCode = "404", description = "Le quiz ou le niveau de difficulté n'existe pas ou est désactivé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @GetMapping(ApiRoutes.Quizzes.BY_ID + ApiRoutes.Questions.STRING)
    public List<QuestionForPlayDTO> getQuestions(
            @PathVariable Long id,
            @RequestParam Long difficultyLevelId) {
        log.info("Requête pour obtenir les questions / réponses du quiz avec l'ID: {}", id);
        List<QuestionForPlayDTO> questions = gameService.findAllQuestionsByQuizIdForPlay(id, difficultyLevelId);
        log.info("Questions / réponses récupérées pour le quiz avec l'ID: {}", id);
        return questions;
    }


    @Operation(summary = "Vérifier la réponse à une question",
            description = "Vérifie si la réponse soumise par l'utilisateur est correcte pour une question donnée.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La réponse a été vérifiée avec succès.",
                    content = {@Content(schema = @Schema(implementation = CheckAnswerResultDTO.class))}),
            @ApiResponse(responseCode = "400", description = "La requête est invalide ou les données sont manquantes.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Une ou plusieurs entités n'existent pas ou sont désactivées.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PostMapping(ApiRoutes.Others.CHECK_ANSWER)
    public ResponseEntity<CheckAnswerResultDTO> checkAnswer(@RequestBody @Valid QuestionAnswerRequestDTO request) {
        CheckAnswerResultDTO result = gameService.checkAnswer(request);
        return ResponseEntity.ok(result);
    }

}
