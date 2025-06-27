package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.service.MasteryLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Tag(name = "ADMIN : Niveau de maîtrise", description = "Gestion des niveaux de maîtrise pour l'administration")
@RestController
@RequestMapping(ApiRoutes.MasteryLevels.ADMIN_MASTERY_LEVELS)
public class MasteryLevelAdminController {

    private final MasteryLevelService masteryLevelService;

    public MasteryLevelAdminController(MasteryLevelService masteryLevelService) {
        this.masteryLevelService = masteryLevelService;
    }


    @Operation(summary = "Obtenir la liste des types de quiz", description = "Obtient la liste des types de quiz avec uniquement l'ID et le nom.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des types de quiz a été trouvée."),
    })
    @GetMapping
    public ResponseEntity<List<IdAndNameProjection>> getAllMasteryLevels() {
        log.info("Requête pour obtenir la liste des niveaux de maîtrise.");
        List<IdAndNameProjection> masteryLevels = masteryLevelService.findAllMasteryLevel();
        log.info("Liste des niveaux de maîtrise récupérée.");
        return ResponseEntity.ok(masteryLevels);
    }
}
