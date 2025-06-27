package com.dassonville.api.controller;


import com.dassonville.api.constant.ApiRoutes;
import com.dassonville.api.dto.request.BooleanRequestDTO;
import com.dassonville.api.dto.request.CategoryUpsertDTO;
import com.dassonville.api.dto.response.CategoryAdminDTO;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.service.CategoryService;
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
@Tag(name = "ADMIN - Thème : Catégories", description = "Gestion des catégories pour les administrateurs")
@RestController
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @Operation(summary = "Obtenir la liste des nom de catégories par thème", description = "Obtient la liste des catégories pour un thème par son ID. Ne renvoie que l'ID et le nom de la catégorie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La liste des catégories a été trouvée."),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.Themes.ADMIN_BY_ID + ApiRoutes.Categories.STRING)
    public ResponseEntity<List<IdAndNameProjection>> getAllByTheme(@PathVariable Long id) {
        log.info("Requête pour obtenir la liste des catégories pour le thème avec l'ID: {}", id);
        List<IdAndNameProjection> categories = categoryService.findAllByTheme(id);
        log.info("Liste des catégories récupérée.");
        return ResponseEntity.ok(categories);
    }


    @Operation(summary = "Obtenir une catégorie", description = "Obtient une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La catégorie a été trouvée."),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping(ApiRoutes.Categories.ADMIN_BY_ID)
    public ResponseEntity<CategoryAdminDTO> getById(@PathVariable Long id) {
        log.info("Requête pour obtenir la catégorie avec l'ID: {}", id);
        CategoryAdminDTO category = categoryService.findById(id);
        log.info("Catégorie récupérée avec l'ID: {}", id);
        return ResponseEntity.ok(category);
    }


    @Operation(summary = "Créer une catégorie", description = "Créer une nouvelle catégorie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La catégorie a été créée."),
            @ApiResponse(responseCode = "400", description = "La catégorie avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "Le thème avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La catégorie avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping(ApiRoutes.Categories.ADMIN_QUESTIONS_POST)
    public ResponseEntity<CategoryAdminDTO> create(@PathVariable Long id, @RequestBody @Valid CategoryUpsertDTO category) {
        log.info("Requête pour créer une catégorie.");
        CategoryAdminDTO createdCategory = categoryService.create(id, category);
        log.info("Catégorie créée avec l'ID: {}", createdCategory.id());

        URI location = ServletUriComponentsBuilder.fromPath(ApiRoutes.Categories.ADMIN_BY_ID)
                .buildAndExpand(createdCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }


    @Operation(summary = "Modifier une catégorie", description = "Met à jour une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La catégorie a été mise à jour."),
            @ApiResponse(responseCode = "400", description = "La catégorie avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "409", description = "La catégorie avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PutMapping(ApiRoutes.Categories.ADMIN_BY_ID)
    public ResponseEntity<CategoryAdminDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryUpsertDTO category) {
        log.info("Requête pour mettre à jour une catégorie.");
        CategoryAdminDTO categoryUpdated = categoryService.update(id, category);
        log.info("Catégorie mise à jour avec l'ID: {}", id);
        return ResponseEntity.ok(categoryUpdated);
    }


    @Operation(summary = "Supprimer une catégorie", description = "Supprime une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La catégorie a été supprimée."),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping(ApiRoutes.Categories.ADMIN_BY_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Requête pour supprimer une catégorie.");
        categoryService.delete(id);
        log.info("Catégorie supprimée avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Activer / désactiver une catégorie", description = "Active / désactive une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La catégorie a été activée / désactivée."),
            @ApiResponse(responseCode = "400", description = "Les données fournies pour activer / désactiver la catégorie sont invalides.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PatchMapping(ApiRoutes.Categories.ADMIN_VISIBILITY_PATCH)
    public ResponseEntity<Void> updateVisibility(@PathVariable Long id, @RequestBody @Valid BooleanRequestDTO booleanRequestDTO) {
        log.info("Requête pour activer / désactiver la catégorie avec l'ID: {}", id);
        categoryService.updateVisibility(id, booleanRequestDTO.value());
        log.info("Catégorie activée / désactivée avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
