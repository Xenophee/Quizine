package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.service.AnswerService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Tag(name = "ADMIN - Quiz : Réponses", description = "Gestion des réponses pour les administrateurs")
@RestController
public class AnswerAdminController {

    private final AnswerService answerService;

    public AnswerAdminController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @Operation(summary = "Créer une réponse", description = "Crée une nouvelle réponse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La réponse a été créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies de la réponse sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "La question avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Le type de la question ne permet d'enregistrer ce type de réponse.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La réponse existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PostMapping(ApiRoutes.Answers.ADMIN_ANSWERS_POST)
    public ResponseEntity<AnswerAdminDTO> create(@PathVariable Long id, @RequestBody @Valid ClassicAnswerUpsertDTO answer) {
        log.info("Requête pour créer une réponse.");
        AnswerAdminDTO createdAnswer = answerService.create(id, answer);
        log.info("Réponse créée avec succès.");

        URI location = ServletUriComponentsBuilder.fromPath(ApiRoutes.Answers.ADMIN_BY_ID)
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
            @ApiResponse(responseCode = "422", description = "La modification demandée n'est pas permise, car elle enfreindrait les règles métier.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La réponse existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PutMapping(ApiRoutes.Answers.ADMIN_BY_ID)
    public ResponseEntity<AnswerAdminDTO> update(@PathVariable Long id, @RequestBody @Valid ClassicAnswerUpsertDTO answer) {
        log.info("Requête pour mettre à jour la réponse avec l'ID {}.", id);
        AnswerAdminDTO updatedAnswer = answerService.update(id, answer);
        log.info("Réponse mise à jour avec succès.");
        return ResponseEntity.ok(updatedAnswer);
    }


    @Operation(summary = "Supprimer une réponse", description = "Supprime une réponse existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La réponse a été supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "La réponse avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "La suppression de la réponse n'est pas permise, car elle enfreindrait les règles métier.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @DeleteMapping(ApiRoutes.Answers.ADMIN_BY_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Requête pour supprimer la réponse avec l'ID {}.", id);
        answerService.delete(id);
        log.info("Réponse supprimée avec succès.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver une réponse", description = "Active / désactive une question existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La réponse a été activée / désactivée avec succès."),
            @ApiResponse(responseCode = "404", description = "La réponse avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "La modification de la visibilité de la réponse n'est pas permise, car elle enfreindrait les règles métier.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PatchMapping(ApiRoutes.Answers.ADMIN_VISIBILITY_PATCH)
    public ResponseEntity<Void> updateVisibility(@PathVariable Long id, @RequestBody BooleanRequestDTO request) {
        log.info("Requête pour activer / désactiver la réponse avec l'ID {}.", id);
        answerService.updateVisibility(id, request.value());
        log.info("La réponse a été activée / désactivée avec succès.");
        return ResponseEntity.noContent().build();
    }
}
