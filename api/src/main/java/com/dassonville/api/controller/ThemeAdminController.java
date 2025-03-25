package com.dassonville.api.controller;

import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.service.ThemeService;
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


@Tag(name = "Gestion de thèmes - admin")
@RestController
@RequestMapping("/api/admin/themes")
public class ThemeAdminController {

    private static final Logger logger = LoggerFactory.getLogger(ThemeAdminController.class);

    private final ThemeService themeService;

    public ThemeAdminController(ThemeService themeService) {
        this.themeService = themeService;
    }


    @Operation(summary = "Obtenir la liste des thèmes", description = "Obtient la liste des thèmes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des thèmes a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<List<ThemeAdminDTO>> getAllThemes() {
        logger.info("Requête pour obtenir la liste des thèmes.");
        List<ThemeAdminDTO> themes = themeService.getAllThemes();
        logger.info("Liste des thèmes récupérée.");
        return ResponseEntity.ok(themes);
    }


    @Operation(summary = "Obtenir un thème", description = "Obtient un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le thème a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ThemeAdminDTO> getThemeById(@PathVariable long id) {
        logger.info("Requête pour obtenir le thème avec l'ID: {}", id);
        ThemeAdminDTO theme = themeService.findByIdForAdmin(id);
        logger.info("Thème trouvé avec l'ID: {}", id);
        return ResponseEntity.ok(theme);
    }


    @Operation(summary = "Créer un thème", description = "Crée un nouveau thème")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Le thème a été créé."),
            @ApiResponse(responseCode = "400", description = "Le thème avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "Le thème avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping
    public ResponseEntity<ThemeAdminDTO> createTheme(@RequestBody @Valid ThemeUpsertDTO theme) {
        logger.info("Requête pour créer un nouveau thème.");
        ThemeAdminDTO createdTheme = themeService.create(theme);
        logger.info("Nouveau thème créé avec l'ID: {}", createdTheme.id());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTheme.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTheme);
    }


    @Operation(summary = "Modifier un thème", description = "Met à jour un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le thème a été mis à jour."),
            @ApiResponse(responseCode = "400", description = "Le thème avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "Le thème avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<ThemeAdminDTO> updateTheme(@PathVariable long id, @RequestBody @Valid ThemeUpsertDTO theme) {
        logger.info("Requête pour mettre à jour le thème avec l'ID: {}", id);
        ThemeAdminDTO themeUpdated = themeService.update(id, theme);
        logger.info("Thème mis à jour avec l'ID: {}", id);
        return ResponseEntity.ok(themeUpdated);
    }


    @Operation(summary = "Supprimer un thème", description = "Supprime un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le thème a été supprimé."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable long id) {
        logger.info("Requête pour supprimer le thème avec l'ID: {}", id);
        themeService.delete(id);
        logger.info("Thème supprimé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver un thème", description = "Active / désactive un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le thème a été activé / désactivé."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver le thème sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> disableTheme(@PathVariable long id, @RequestBody @Valid ToggleDisableRequestDTO toggleDisableRequestDTO) {
        logger.info("Requête pour activer / désactiver le thème avec l'ID: {}", id);
        themeService.toggleDisable(id, toggleDisableRequestDTO);
        logger.info("Thème activé / désactivé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }

}
