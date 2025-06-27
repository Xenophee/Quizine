package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.projection.CodeAndNameProjection;
import com.dassonville.api.service.QuizTypeService;
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
@Tag(name = "ADMIN : Types de quiz", description = "Gestion des types de quiz pour l'administration")
@RestController
@RequestMapping(ApiRoutes.QuizTypes.ADMIN_QUIZ_TYPES)
public class QuizTypeAdminController {

    private final QuizTypeService quizTypeService;

    public QuizTypeAdminController(QuizTypeService quizTypeService) {
        this.quizTypeService = quizTypeService;
    }


    @Operation(summary = "Obtenir la liste des types de quiz", description = "Obtient la liste des types de quiz avec uniquement l'ID et le nom.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des types de quiz a été trouvée."),
    })
    @GetMapping
    public ResponseEntity<List<CodeAndNameProjection>> getAll() {
        log.info("Requête pour obtenir la liste des types de quiz.");
        List<CodeAndNameProjection> quizTypes = quizTypeService.findAllQuizTypes();
        log.info("Liste des types de quiz récupérée.");
        return ResponseEntity.ok(quizTypes);
    }
}
