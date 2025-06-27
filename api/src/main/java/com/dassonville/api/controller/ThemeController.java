package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.response.ThemePublicDTO;
import com.dassonville.api.service.ThemeService;
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
@Tag(name = "PUBLIC", description = "Endpoints accessibles publiquement")
@RestController
@RequestMapping(ApiRoutes.Themes.BASE)
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }


    @Operation(summary = "Obtenir la liste des thèmes actifs", description = "Obtient la liste des thèmes actifs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des thèmes actifs a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<List<ThemePublicDTO>> getAllActive() {
        log.info("Requête pour obtenir la liste des thèmes actifs.");
        List<ThemePublicDTO> themes = themeService.getAllActive();
        log.info("Liste des thèmes actifs récupérée.");
        return ResponseEntity.ok(themes);
    }
}
