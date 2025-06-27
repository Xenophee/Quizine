package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.response.DifficultyLevelPublicDTO;
import com.dassonville.api.service.DifficultyLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "PUBLIC", description = "Endpoints accessibles publiquement")
@RestController
public class DifficultyLevelController {

    private final DifficultyLevelService difficultyLevelService;

    public DifficultyLevelController(DifficultyLevelService difficultyLevelService) {
        this.difficultyLevelService = difficultyLevelService;
    }


    @Operation(summary = "Obtenir la liste des niveaux de difficulté actif", description = "Obtient la liste des niveaux de difficulté actif pour un quiz donné.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des niveaux de difficulté actif récupérée avec succès."),
            @ApiResponse(responseCode = "404", description = "Le quiz n'existe pas ou est désactivé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "Le quiz ne réunit pas les conditions nécessaires pour proposer des niveaux de difficulté.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.Quizzes.BY_ID + ApiRoutes.DifficultyLevels.STRING)
    public ResponseEntity<List<DifficultyLevelPublicDTO>> getAllActiveDifficultyLevels(@PathVariable Long id) {
        log.info("Requête pour obtenir la liste des niveaux de difficulté actif.");
        List<DifficultyLevelPublicDTO> difficultyLevels = difficultyLevelService.getAllActiveDifficultyLevels(id);
        log.info("Liste des niveaux de difficulté actif récupérée.");
        return ResponseEntity.ok(difficultyLevels);
    }
}
