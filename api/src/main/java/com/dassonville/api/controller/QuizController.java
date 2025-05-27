package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.*;
import com.dassonville.api.service.QuestionService;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "PUBLIC", description = "Endpoints accessibles publiquement")
@RestController
@RequestMapping(ApiRoutes.Quizzes.BASE)
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;
    private final QuestionService questionService;

    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }


    @Operation(summary = "Obtenir la liste des quiz actifs (avec pagination)",
            description = "Obtient la liste des quiz actifs.<br>" +
                    "Le nombre de quiz par page est de 10 par défaut.<br>" +
                    "Le tri par défaut est par date de création (du plus récent au plus ancien).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des quiz a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<QuizPublicDTO>>> getQuizzes(
            @RequestParam(required = false) List<Long> themeIds,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<QuizPublicDTO> assembler
    ) {
        logger.info("Requête pour obtenir la liste des quiz.");
        Page<QuizPublicDTO> quizzes = quizService.findAllByThemeIdsForUser(themeIds, pageable);
        PagedModel<EntityModel<QuizPublicDTO>> pagedModel = assembler.toModel(quizzes, EntityModel::of);
        logger.info("Liste des quiz récupérée.");
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
    public ResponseEntity<QuizPublicDetailsDTO> getQuizDetails(@PathVariable Long id) {
        logger.info("Requête pour obtenir les détails du quiz avec l'ID: {}", id);
        QuizPublicDetailsDTO quizDetails = quizService.findByIdForUser(id);
        logger.info("Détails du quiz récupérés pour l'ID: {}", id);
        return ResponseEntity.ok(quizDetails);
    }


    @Operation(summary = "Obtenir les questions d'un quiz actif pour le jeu",
            description = "Obtient les questions d'un quiz actif par son ID et le niveau de difficulté.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Les questions du quiz ont été trouvées."),
            @ApiResponse(responseCode = "404", description = "Le quiz ou le niveau de difficulté n'existe pas ou est désactivé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
    })
    @GetMapping(ApiRoutes.ID + ApiRoutes.Questions.STRING)
    public List<QuestionForPlayDTO> getQuestions(
            @PathVariable Long id,
            @RequestParam Long difficultyLevelId) {
        logger.info("Requête pour obtenir les questions / réponses du quiz avec l'ID: {}", id);
        List<QuestionForPlayDTO> questions = quizService.findAllQuestionsByQuizIdForPlay(id, difficultyLevelId);
        logger.info("Questions / réponses récupérées pour le quiz avec l'ID: {}", id);
        return questions;
    }


    @Operation(summary = "Vérifier la réponse à une question",
            description = "Vérifie si les réponses soumises à une question sont correctes et retourne la solution.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La réponse a été vérifiée avec succès."),
            @ApiResponse(responseCode = "404", description = "La question n'existe pas ou n'appartient pas au quiz.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "400", description = "Les réponses soumises ne correspondent pas à la question.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping(ApiRoutes.Questions.CHECK_ANSWER_CHOICE)
    public ResponseEntity<CheckAnswerResultDTO> checkAnswer(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody @Valid CheckAnswerChoicesRequestDTO request) {
        logger.info("Vérification des réponses pour la question {} du quiz {}", questionId, quizId);
        CheckAnswerResultDTO result = questionService.checkAnswerByChoice(
                quizId, questionId, request.answerIds());
        logger.info("Résultat de la vérification pour la question {} du quiz {}: {}", questionId, quizId, result.isCorrect());
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Vérifier la réponse textuelle à une question",
            description = "Vérifie si les réponses textuelles soumises à une question sont correctes et retourne la solution.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La réponse textuelle a été vérifiée avec succès."),
            @ApiResponse(responseCode = "404", description = "La question n'existe pas ou n'appartient pas au quiz.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping(ApiRoutes.Questions.CHECK_ANSWER_TEXT)
    public ResponseEntity<CheckAnswerResultDTO> checkText(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody CheckAnswerTextsRequestDTO request
    ) {
        logger.info("Vérification des réponses textuelles pour la question {} du quiz {}", questionId, quizId);
        CheckAnswerResultDTO result = questionService.checkAnswerByText(quizId, questionId, request.submittedTexts());
        logger.info("Résultat de la vérification des réponses textuelles pour la question {} du quiz {}: {}", questionId, quizId, result.isCorrect());
        return ResponseEntity.ok(result);
    }


}
