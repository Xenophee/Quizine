package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.QuizUpsertDTO;
import com.dassonville.api.dto.response.QuizAdminDTO;
import com.dassonville.api.dto.response.QuizAdminDetailsDTO;
import com.dassonville.api.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;


@Slf4j
@Tag(name = "ADMIN - Quiz", description = "Gestion des quiz pour les administrateurs")
@RestController
@RequestMapping(ApiRoutes.Quizzes.ADMIN_QUIZZES)
public class QuizAdminController {

    private final QuizService quizService;

    public QuizAdminController(QuizService quizService) {
        this.quizService = quizService;
    }


    @Operation(summary = "Obtenir la liste des quiz (avec pagination)",
            description = """
                    Obtient la liste des quiz actifs.<br>
                    Le nombre de quiz par thème est de 20 par défaut.<br>
                    Le tri par défaut est par titre de quiz.<br>
                    Pour obtenir la liste des quiz désactivés, utilisez le paramètre <code>visible=false</code>.<br>
                    Pour obtenir tous les quiz, utilisez le paramètre <code>visible=null</code>.<br>
                    Le nombre de quiz par page est de 10 par défaut.<br>
                    Le tri par défaut est par titre de quiz.<br>
                    Dans la démo Swagger, écrivez le <code>sort</code> de cette manière : <code>"sort": "title"</code> et non pas sous forme de tableau comme l'indique l'exemple.<br>
                    Côté front, la requête doit ressembler à ceci : <code>/api/admin/quizzes?themeId=4&visible=true&sort=title</code>
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des quiz a été trouvée."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<QuizAdminDTO>>> getAllByTheme(
            @RequestParam Long themeId,
            @RequestParam(required = false) Boolean visible,
            @PageableDefault(size = 20, sort = "title") Pageable pageable,
            PagedResourcesAssembler<QuizAdminDTO> assembler
    ) {
        log.info("Requête pour obtenir la liste des quiz.");
        Page<QuizAdminDTO> groupedQuizzes = quizService.findAllByThemeIdForAdmin(themeId, visible, pageable);
        PagedModel<EntityModel<QuizAdminDTO>> pagedModel = assembler.toModel(groupedQuizzes, EntityModel::of);
        log.info("Liste des quiz récupérée.");
        return ResponseEntity.ok(pagedModel);
    }


    @Operation(summary = "Obtenir un quiz", description = "Obtient un quiz par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le quiz a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<QuizAdminDetailsDTO> getById(@PathVariable Long id) {
        log.info("Requête pour obtenir le quiz avec l'ID: {}", id);
        QuizAdminDetailsDTO quiz = quizService.findByIdForAdmin(id);
        log.info("Quiz trouvé avec succès.");
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
    public ResponseEntity<QuizAdminDetailsDTO> create(@RequestBody @Valid QuizUpsertDTO quiz) {
        log.info("Requête pour créer un quiz.");
        QuizAdminDetailsDTO createdQuiz = quizService.create(quiz);
        log.info("Quiz créé avec succès.");

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
    public ResponseEntity<QuizAdminDetailsDTO> update(@PathVariable Long id, @RequestBody @Valid QuizUpsertDTO quiz) {
        log.info("Requête pour mettre à jour le quiz avec l'ID: {}", id);
        QuizAdminDetailsDTO updatedQuiz = quizService.update(id, quiz);
        log.info("Quiz mis à jour avec succès.");
        return ResponseEntity.ok(updatedQuiz);
    }


    @Operation(summary = "Suppression d'un quiz", description = "Supprime un quiz par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le quiz a été supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping(ApiRoutes.ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Requête pour supprimer le quiz avec l'ID {}.", id);
        quizService.delete(id);
        log.info("Quiz supprimé avec succès.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver un quiz", description = "Active ou désactive un quiz par son ID. Un quiz ne peut être actif que s'il contient au moins " + MINIMUM_QUIZ_QUESTIONS + " questions.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le quiz a été activé / désactivé avec succès."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver le quiz sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le quiz avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Le quiz ne peut pas être activé car il ne contient pas le nombre minimum de questions requis.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping(ApiRoutes.VISIBILITY)
    public ResponseEntity<Void> updateVisibility(@PathVariable Long id, @RequestBody @Valid BooleanRequestDTO request) {
        log.info("Requête pour activer / désactiver le quiz avec l'ID: {}", id);
        quizService.updateVisibility(id, request.value());
        log.info("Quiz activé / désactivé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
