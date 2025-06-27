package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.QuestionUpsertDTO;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.service.QuestionService;
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
@Tag(name = "ADMIN - Quiz : Questions", description = "Gestion des questions pour les administrateurs")
@RestController
public class QuestionAdminController {

    private final QuestionService questionService;

    public QuestionAdminController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @Operation(summary = "Créer une question", description = "Crée une nouvelle question")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La question a été créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies de la question sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Le type du quiz n'autorise pas ce type de question.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La question existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PostMapping(ApiRoutes.Questions.ADMIN_QUESTIONS_POST)
    public ResponseEntity<QuestionAdminDTO> create(@PathVariable Long id, @RequestBody @Valid QuestionUpsertDTO question) {
        log.info("Requête pour créer une question.");
        QuestionAdminDTO createdQuestion = questionService.create(id, question);
        log.info("Question créée avec succès.");

        URI location = ServletUriComponentsBuilder.fromPath(ApiRoutes.Questions.ADMIN_BY_ID)
                .buildAndExpand(createdQuestion.id())
                .toUri();

        return ResponseEntity.created(location).body(createdQuestion);
    }


    @Operation(summary = "Mettre à jour une question", description = "Met à jour une question existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La question a été mise à jour avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies de la question sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "La question avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Les informations fournies ne correspondent pas au schéma attendu pour le type de la question.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La question existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PutMapping(ApiRoutes.Questions.ADMIN_BY_ID)
    public ResponseEntity<QuestionAdminDTO> update(@PathVariable Long id, @RequestBody @Valid QuestionUpsertDTO question) {
        log.info("Requête pour mettre à jour la question avec l'ID {}.", id);
        QuestionAdminDTO updatedQuestion = questionService.update(id, question);
        log.info("Question mise à jour avec succès.");
        return ResponseEntity.ok(updatedQuestion);
    }


    @Operation(summary = "Supprimer une question", description = "Supprime une question existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La question a été supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "La question avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @DeleteMapping(ApiRoutes.Questions.ADMIN_BY_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Requête pour supprimer la question avec l'ID {}.", id);
        questionService.delete(id);
        log.info("Question supprimée avec succès.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver une question", description = "Active / désactive une question existante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La question a été activée / désactivée avec succès."),
            @ApiResponse(responseCode = "404", description = "La question avec l'ID spécifié n'a pas été trouvée.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @PatchMapping(ApiRoutes.Questions.ADMIN_VISIBILITY_PATCH)
    public ResponseEntity<Void> updateVisibility(@PathVariable Long id, @RequestBody BooleanRequestDTO request) {
        log.info("Requête pour activer / désactiver la question avec l'ID {}.", id);
        questionService.updateVisibility(id, request.value());
        log.info("Question activée / désactivée avec succès.");
        return ResponseEntity.noContent().build();
    }
}
