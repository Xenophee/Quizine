package com.dassonville.api.controller;

import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.ThemeUpsertDTO;
import com.dassonville.api.dto.response.ThemeAdminDTO;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.service.ThemeService;
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
import java.util.List;
import java.util.Map;


@Slf4j
@Tag(name = "ADMIN - Thème", description = "Gestion des thèmes pour les administrateurs")
@RestController
@RequestMapping(ApiRoutes.Themes.ADMIN_THEMES)
public class ThemeAdminController {

    private final ThemeService themeService;

    public ThemeAdminController(ThemeService themeService) {
        this.themeService = themeService;
    }



    @Operation(summary = "Obtenir la liste des thèmes en détail", description = "Obtient la liste des thèmes complète avec les catégories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des thèmes a été trouvée.")
    })
    @GetMapping(ApiRoutes.DETAILS)
    public ResponseEntity<List<ThemeAdminDTO>> getAllDetails() {
        log.info("Requête pour obtenir la liste des thèmes.");
        List<ThemeAdminDTO> themes = themeService.getAllDetails();
        log.info("Liste des thèmes récupérée.");
        return ResponseEntity.ok(themes);
    }

    @Operation(summary = "Obtenir la liste des noms de thèmes", description = "Obtient la liste des thèmes avec uniquement l'ID et le nom.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des thèmes a été trouvée.")
    })
    @GetMapping
    public ResponseEntity<List<IdAndNameProjection>> getAll() {
        log.info("Requête pour obtenir la liste des thèmes.");
        List<IdAndNameProjection> themes = themeService.getAll();
        log.info("Liste des thèmes récupérée.");
        return ResponseEntity.ok(themes);
    }


    @Operation(summary = "Obtenir un thème", description = "Obtient un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le thème a été trouvé."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<ThemeAdminDTO> getById(@PathVariable Long id) {
        log.info("Requête pour obtenir le thème avec l'ID: {}", id);
        ThemeAdminDTO theme = themeService.findByIdForAdmin(id);
        log.info("Thème trouvé avec l'ID: {}", id);
        return ResponseEntity.ok(theme);
    }

    @Operation(summary = "Créer un thème", description = "Crée un nouveau thème")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Le thème a été créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Le thème avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "Le thème avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping
    public ResponseEntity<ThemeAdminDTO> create(@RequestBody @Valid ThemeUpsertDTO theme) {
        log.info("Requête pour créer un nouveau thème.");
        ThemeAdminDTO createdTheme = themeService.create(theme);
        log.info("Nouveau thème créé avec l'ID: {}", createdTheme.id());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiRoutes.ID)
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
    @PutMapping(ApiRoutes.ID)
    public ResponseEntity<ThemeAdminDTO> update(@PathVariable Long id, @RequestBody @Valid ThemeUpsertDTO theme) {
        log.info("Requête pour mettre à jour le thème avec l'ID: {}", id);
        ThemeAdminDTO updatedTheme = themeService.update(id, theme);
        log.info("Thème mis à jour avec l'ID: {}", id);
        return ResponseEntity.ok(updatedTheme);
    }


    @Operation(summary = "Supprimer un thème", description = "Supprime un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le thème a été supprimé."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Le thème ne peut pas être supprimé, car il comporte encore des quiz.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping(ApiRoutes.ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Requête pour supprimer le thème avec l'ID: {}", id);
        themeService.delete(id);
        log.info("Thème supprimé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver un thème", description = "Active / désactive un thème par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Le thème a été activé / désactivé."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver le thème sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "422", description = "Le thème ne peut pas être activé / désactivé, car il comporte encore des quiz.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping(ApiRoutes.VISIBILITY)
    public ResponseEntity<Void> updateVisibility(@PathVariable Long id, @RequestBody @Valid BooleanRequestDTO booleanRequestDTO) {
        log.info("Requête pour activer / désactiver le thème avec l'ID: {}", id);
        themeService.updateVisibility(id, booleanRequestDTO.value());
        log.info("Thème activé / désactivé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }

}
