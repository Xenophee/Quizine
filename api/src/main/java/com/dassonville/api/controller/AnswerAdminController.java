package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.service.AnswerService;
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
import java.util.Map;

@Tag(name = "Gestion de réponse - admin")
@RestController
public class AnswerAdminController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerAdminController.class);

    private final AnswerService answerService;

    public AnswerAdminController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @Operation(summary = "Créer une réponse", description = "Crée une nouvelle réponse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La réponse a été créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies de la réponse sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "La réponse existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PostMapping(ApiRoutes.Answers.ADMIN_ANSWERS_POST)
    public ResponseEntity<AnswerAdminDTO> createResponse(@PathVariable long id, @RequestBody @Valid AnswerUpsertDTO answer) {
        logger.info("Requête pour créer une réponse.");
        AnswerAdminDTO createdAnswer = answerService.create(id, answer);
        logger.info("Réponse créée avec succès.");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiRoutes.ID)
                .buildAndExpand(createdAnswer.id())
                .toUri();

        return ResponseEntity.created(location).body(createdAnswer);
    }


    @Operation(summary = "Mettre à jour une réponse", description = "Met à jour une réponse existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La réponse a été mise à jour avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies de la réponse sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "La réponse avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La réponse existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PutMapping(ApiRoutes.Answers.ADMIN_BY_ID)
    public ResponseEntity<AnswerAdminDTO> updateResponse(@PathVariable long id, @RequestBody @Valid AnswerUpsertDTO answer) {
        logger.info("Requête pour mettre à jour la réponse avec l'ID {}.", id);
        AnswerAdminDTO updatedAnswer = answerService.update(id, answer);
        logger.info("Réponse mise à jour avec succès.");
        return ResponseEntity.ok(updatedAnswer);
    }


    @Operation(summary = "Supprimer une réponse", description = "Supprime une réponse existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La réponse a été supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "La réponse avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @DeleteMapping(ApiRoutes.Answers.ADMIN_BY_ID)
    public ResponseEntity<Void> deleteResponse(@PathVariable long id) {
        logger.info("Requête pour supprimer la réponse avec l'ID {}.", id);
        answerService.delete(id);
        logger.info("Réponse supprimée avec succès.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver une réponse", description = "Active / désactive une question existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La réponse a été activée / désactivée avec succès."),
            @ApiResponse(responseCode = "404", description = "La réponse avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PatchMapping(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH)
    public ResponseEntity<Void> toggleVisibility(@PathVariable long id, @RequestBody BooleanRequestDTO request) {
        logger.info("Requête pour activer / désactiver la réponse avec l'ID {}.", id);
        answerService.toggleVisibility(id, request.value());
        logger.info("La réponse a été activée / désactivée avec succès.");
        return ResponseEntity.noContent().build();
    }
}
