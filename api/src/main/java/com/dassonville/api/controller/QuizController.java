package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.response.QuizPublicDTO;
import com.dassonville.api.dto.response.QuizPublicDetailsDTO;
import com.dassonville.api.service.QuestionService;
import com.dassonville.api.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "PUBLIC", description = "Endpoints accessibles publiquement")
@RestController
@RequestMapping(ApiRoutes.Quizzes.BASE)
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
    }


    @Operation(summary = "Obtenir la liste des quiz actifs (avec pagination)",
            description = """
                    Obtient la liste des quiz actifs.<br>
                    Le nombre de quiz par page est de 10 par défaut.<br>
                    Le tri par défaut est par date de création (du plus récent au plus ancien).
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des quiz a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<QuizPublicDTO>>> getAllActive(
            @RequestParam(required = false) List<Long> themeIds,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<QuizPublicDTO> assembler
    ) {
        log.info("Requête pour obtenir la liste des quiz.");
        Page<QuizPublicDTO> quizzes = quizService.findAllByThemeIdsForUser(themeIds, pageable);
        PagedModel<EntityModel<QuizPublicDTO>> pagedModel = assembler.toModel(quizzes, EntityModel::of);
        log.info("Liste des quiz récupérée.");
        return ResponseEntity.ok(pagedModel);
    }


    @Operation(summary = "Obtenir les détails d'un quiz actif",
            description = "Obtient les détails d'un quiz actif par son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le quiz a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le quiz n'existe pas ou est désactivé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<QuizPublicDetailsDTO> getActiveById(@PathVariable Long id) {
        log.info("Requête pour obtenir les détails du quiz avec l'ID: {}", id);
        QuizPublicDetailsDTO quizDetails = quizService.findByIdForUser(id);
        log.info("Détails du quiz récupérés pour l'ID: {}", id);
        return ResponseEntity.ok(quizDetails);
    }
}
