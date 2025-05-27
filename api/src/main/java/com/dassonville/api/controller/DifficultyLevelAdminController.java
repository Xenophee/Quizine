package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ReorderRequestDTO;
import com.dassonville.api.service.DifficultyLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;


@Tag(name = "ADMIN - Niveaux de difficulté", description = "Gestion des niveaux de difficulté pour les administrateurs")
@RestController
@RequestMapping(ApiRoutes.DifficultyLevels.ADMIN_DIFFICULTY_LEVELS)
public class DifficultyLevelAdminController {

    private static final Logger logger = LoggerFactory.getLogger(DifficultyLevelAdminController.class);

    private final DifficultyLevelService difficultyLevelService;

    public DifficultyLevelAdminController(DifficultyLevelService difficultyLevelService) {
        this.difficultyLevelService = difficultyLevelService;
    }


    @Operation(summary = "Obtenir la liste des niveaux de difficulté", description = "Obtient la liste des niveaux de difficulté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des niveaux de difficulté a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<List<DifficultyLevelAdminDTO>> getAllDifficultyLevels() {
        logger.info("Requête pour obtenir la liste des niveaux de difficulté.");
        List<DifficultyLevelAdminDTO> difficultyLevels = difficultyLevelService.getAllDifficultyLevels();
        logger.info("Liste des niveaux de difficulté récupérée.");
        return ResponseEntity.ok(difficultyLevels);
    }


    @Operation(summary = "Obtenir un niveau de difficulté", description = "Obtient un niveau de difficulté par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le niveau de difficulté a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le niveau de difficulté avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<DifficultyLevelAdminDTO> getDifficultyLevelById(@PathVariable Long id) {
        logger.info("Requête pour obtenir le niveau de difficulté avec l'ID: {}", id);
        DifficultyLevelAdminDTO difficultyLevel = difficultyLevelService.findById(id);
        logger.info("Niveau de difficulté récupéré avec l'ID: {}", id);
        return ResponseEntity.ok(difficultyLevel);
    }


    @Operation(summary = "Créer un niveau de difficulté", description = "Crée un niveau de difficulté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Le niveau de difficulté a été créé."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour créer le niveau de difficulté sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "Un niveau de difficulté existe déjà avec le même nom.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping
    public ResponseEntity<DifficultyLevelAdminDTO> createDifficultyLevel(@RequestBody @Valid DifficultyLevelUpsertDTO difficultyLevelUpsertDTO) {
        logger.info("Requête pour créer un niveau de difficulté.");
        DifficultyLevelAdminDTO createdDifficultyLevel = difficultyLevelService.create(difficultyLevelUpsertDTO);
        logger.info("Niveau de difficulté créé avec l'ID: {}", createdDifficultyLevel.id());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiRoutes.ID)
                .buildAndExpand(createdDifficultyLevel.id())
                .toUri();

        return ResponseEntity.created(location).body(createdDifficultyLevel);
    }


    @Operation(summary = "Modifier un niveau de difficulté", description = "Modifie un niveau de difficulté par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le niveau de difficulté a été modifié."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour modifier le niveau de difficulté sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le niveau de difficulté avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "Un niveau de difficulté existe déjà avec le même nom.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PutMapping(ApiRoutes.ID)
    public ResponseEntity<DifficultyLevelAdminDTO> updateDifficultyLevel(@PathVariable Long id, @RequestBody @Valid DifficultyLevelUpsertDTO difficultyLevelUpsertDTO) {
        logger.info("Requête pour modifier le niveau de difficulté avec l'ID: {}", id);
        DifficultyLevelAdminDTO updatedDifficultyLevel = difficultyLevelService.update(id, difficultyLevelUpsertDTO);
        logger.info("Niveau de difficulté modifié avec l'ID: {}", id);
        return ResponseEntity.ok(updatedDifficultyLevel);
    }


    @Operation(summary = "Supprimer un niveau de difficulté", description = "Supprime un niveau de difficulté par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Le niveau de difficulté a été supprimé."),
            @ApiResponse(responseCode = "404", description = "Le niveau de difficulté avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "Le niveau de difficulté est une référence et ne peut pas être supprimé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping(ApiRoutes.ID)
    public ResponseEntity<Void> deleteDifficultyLevel(@PathVariable Long id) {
        logger.info("Requête pour supprimer le niveau de difficulté avec l'ID: {}", id);
        difficultyLevelService.delete(id);
        logger.info("Niveau de difficulté supprimé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver un niveau de difficulté", description = "Active / désactive un niveau de difficulté par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Le niveau de difficulté a été activé / désactivé."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver le niveau de difficulté sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le niveau de difficulté avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping(ApiRoutes.VISIBILITY)
    public ResponseEntity<Void> updateDifficultyLevelVisibility(@PathVariable Long id, @RequestBody @Valid BooleanRequestDTO booleanRequestDTO) {
        logger.info("Requête pour activer / désactiver le niveau de difficulté avec l'ID: {}", id);
        difficultyLevelService.updateVisibility(id, booleanRequestDTO.value());
        logger.info("Niveau de difficulté activé / désactivé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Mettre à jour l'ordre d'affichage des niveaux de difficulté", description = "Met à jour l'ordre d'affichage des niveaux de difficulté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'ordre d'affichage des niveaux de difficulté a été mis à jour."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour mettre à jour l'ordre d'affichage sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Un ou plusieurs niveaux de difficulté avec les ID spécifiés n'ont pas été trouvés.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PatchMapping(ApiRoutes.REORDER)
    public ResponseEntity<Void> reorder(@RequestBody @Valid ReorderRequestDTO request) {
        difficultyLevelService.updateDisplayOrder(request.orderedIds());
        return ResponseEntity.noContent().build();
    }
}
