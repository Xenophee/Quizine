package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.service.DifficultyLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Public", description = "Endpoints accessibles publiquement")
@RestController
@RequestMapping(ApiRoutes.DifficultyLevels.BASE)
public class DifficultyLevelController {

    private static final Logger logger = LoggerFactory.getLogger(DifficultyLevelController.class);

    private final DifficultyLevelService difficultyLevelService;

    public DifficultyLevelController(DifficultyLevelService difficultyLevelService) {
        this.difficultyLevelService = difficultyLevelService;
    }


    @Operation(summary = "Obtenir la liste des niveaux de difficulté actif", description = "Obtient la liste des niveaux de difficulté actif")
    @GetMapping
    public ResponseEntity<List<DifficultyLevelPublicDTO>> getAllActiveDifficultyLevels() {
        logger.info("Requête pour obtenir la liste des niveaux de difficulté actif.");
        List<DifficultyLevelPublicDTO> difficultyLevels = difficultyLevelService.getAllActiveDifficultyLevels();
        logger.info("Liste des niveaux de difficulté actif récupérée.");
        return ResponseEntity.ok(difficultyLevels);
    }
}
