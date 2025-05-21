package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.dto.QuizAdminDTO;
import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;


@Tag(name = "ADMIN - Quiz", description = "Gestion des quiz pour les administrateurs")
@RestController
@RequestMapping(ApiRoutes.Quizzes.ADMIN_QUIZZES)
public class QuizAdminController {

    private static final Logger logger = LoggerFactory.getLogger(QuizAdminController.class);

    private final QuizService quizService;

    public QuizAdminController(QuizService quizService) {
        this.quizService = quizService;
    }


    @Operation(summary = "Obtenir la liste des quiz",
            description = "Obtient la liste des quiz actifs.<br>" +
                    "Pour obtenir la liste des quiz désactivés, utilisez le paramètre <code>visible=false</code>.<br>" +
                    "Pour obtenir tous les quiz, utilisez le paramètre <code>visible=null</code>.<br>" +
                    "Le nombre de quiz par page est de 10 par défaut.<br>" +
                    "Le tri par défaut est par titre de quiz.<br>" +
                    "Dans la démo Swagger, écrivez le <code>sort</code> de cette manière : <code>\"sort\": \"title\"</code> et non pas sous forme de tableau comme l'indique l'exemple.<br>" +
                    "Côté front, la requête doit ressembler à ceci : <code>/api/admin/quizzes?themeId=4&visible=true&sort=title</code>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des quiz a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<Page<QuizAdminDTO>> getQuizzesByTheme(
            @RequestParam long themeId,
            @RequestParam(required = false) Boolean visible,
            @PageableDefault(size = 10, sort = "title") Pageable pageable
    ) {
        logger.info("Requête pour obtenir la liste des quiz.");
        Page<QuizAdminDTO> groupedQuizzes = quizService.getQuizzesByTheme(themeId, visible, pageable);
        logger.info("Liste des quiz récupérée.");
        return ResponseEntity.ok(groupedQuizzes);
    }


    @Operation(summary = "Obtenir un quiz", description = "Obtient un quiz par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le quiz a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<QuizAdminDetailsDTO> getQuizById(@PathVariable long id) {
        logger.info("Requête pour obtenir le quiz avec l'ID: {}", id);
        QuizAdminDetailsDTO quiz = quizService.findByIdForAdmin(id);
        logger.info("Quiz trouvé avec succès.");
        return ResponseEntity.ok(quiz);
    }


    @Operation(summary = "Créer un quiz", description = "Crée un nouveau quiz")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Le quiz a été créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour créer le quiz sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "Le quiz avec le titre spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping
    public ResponseEntity<QuizAdminDetailsDTO> createQuiz(@RequestBody @Valid QuizUpsertDTO quiz) {
        logger.info("Requête pour créer un quiz.");
        QuizAdminDetailsDTO createdQuiz = quizService.create(quiz);
        logger.info("Quiz créé avec succès.");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiRoutes.ID)
                .buildAndExpand(createdQuiz.id())
                .toUri();

        return ResponseEntity.created(location).body(createdQuiz);
    }


    @Operation(summary = "Mettre à jour un quiz", description = "Met à jour les informations générales d'un quiz par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le quiz a été mis à jour avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour mettre à jour le quiz sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "Le quiz avec le titre spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PutMapping(ApiRoutes.ID)
    public ResponseEntity<QuizAdminDetailsDTO> updateQuiz(@PathVariable long id, @RequestBody @Valid QuizUpsertDTO quiz) {
        logger.info("Requête pour mettre à jour le quiz avec l'ID: {}", id);
        QuizAdminDetailsDTO updatedQuiz = quizService.update(id, quiz);
        logger.info("Quiz mis à jour avec succès.");
        return ResponseEntity.ok(updatedQuiz);
    }


    @Operation(summary = "Suppression d'un quiz", description = "Supprime un quiz par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le quiz a été supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping(ApiRoutes.ID)
    public ResponseEntity<Void> deleteQuiz(@PathVariable long id) {
        logger.info("Requête pour supprimer le quiz avec l'ID {}.", id);
        quizService.delete(id);
        logger.info("Quiz supprimé avec succès.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver un quiz", description = "Active ou désactive un quiz par son ID. Un quiz ne peut être actif que s'il contient au moins " + MINIMUM_QUIZ_QUESTIONS + " questions.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le quiz a été activé / désactivé avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver le quiz sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping(ApiRoutes.VISIBILITY)
    public ResponseEntity<Void> toggleVisibility(@PathVariable long id, @RequestBody @Valid BooleanRequestDTO request) {
        logger.info("Requête pour activer / désactiver le quiz avec l'ID: {}", id);
        quizService.updateVisibility(id, request.value());
        logger.info("Quiz activé / désactivé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
