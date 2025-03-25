package com.dassonville.api.controller;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.service.CategoryService;
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
import java.util.Map;


@Tag(name = "Gestion de catégories - admin")
@RestController
@RequestMapping("/api/admin/categories")
public class CategoryAdminController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryAdminController.class);

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Operation(summary = "Obtenir une catégorie", description = "Obtient une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La catégorie a été trouvée."),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryAdminDTO> getCategoryById(@PathVariable long id) {
        logger.info("Requête pour obtenir la catégorie avec l'ID: {}", id);
        CategoryAdminDTO category = categoryService.findById(id);
        logger.info("Catégorie récupérée avec l'ID: {}", id);
        return ResponseEntity.ok(category);
    }


    @Operation(summary = "Créer une catégorie", description = "Créer une nouvelle catégorie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La catégorie a été créée."),
            @ApiResponse(responseCode = "400", description = "La catégorie avec les données spécifiées n'est pas valide.",
                    content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "409", description = "La catégorie avec le nom spécifié existe déjà.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @PostMapping
    public ResponseEntity<CategoryAdminDTO> createCategory(@RequestBody @Valid CategoryUpsertDTO category) {
        logger.info("Requête pour créer une catégorie.");
        CategoryAdminDTO createdCategory = categoryService.create(category);
        logger.info("Catégorie créée avec l'ID: {}", createdCategory.id());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
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
    @PutMapping("/{id}")
    public ResponseEntity<CategoryAdminDTO> updateCategory(@PathVariable long id, @RequestBody @Valid CategoryUpsertDTO category) {
        logger.info("Requête pour mettre à jour une catégorie.");
        CategoryAdminDTO categoryUpdated = categoryService.update(id, category);
        logger.info("Catégorie mise à jour avec l'ID: {}", id);
        return ResponseEntity.ok(categoryUpdated);
    }


    @Operation(summary = "Supprimer une catégorie", description = "Supprime une catégorie par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La catégorie a été supprimée."),
            @ApiResponse(responseCode = "404", description = "La catégorie avec l'ID spécifié n'a pas été trouvé.",
                    content = {@Content(schema = @Schema(implementation = Error.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        logger.info("Requête pour supprimer une catégorie.");
        categoryService.delete(id);
        logger.info("Catégorie supprimée avec l'ID: {}", id);
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
    @PatchMapping("/{id}")
    public ResponseEntity<Void> disableCategory(@PathVariable long id, @RequestBody @Valid ToggleDisableRequestDTO toggleDisableRequestDTO) {
        logger.info("Requête pour activer / désactiver la catégorie avec l'ID: {}", id);
        categoryService.toggleDisable(id, toggleDisableRequestDTO);
        logger.info("Catégorie activée / désactivée avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
