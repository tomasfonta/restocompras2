package com.tf.restocompras.controller;

import com.tf.restocompras.model.ingredient.IngredientCreateRequestDto;
import com.tf.restocompras.model.ingredient.IngredientResponseDto;
import com.tf.restocompras.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@Tag(name = "Ingredient", description = "Endpoints for managing ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Get all ingredients", description = "Returns a list of all ingredients")
    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @Operation(summary = "Get ingredient by ID", description = "Returns an ingredient by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @Operation(summary = "Create a new ingredient", description = "Creates a new ingredient")
    @PostMapping
    public ResponseEntity<IngredientResponseDto> createIngredient(@RequestBody IngredientCreateRequestDto createDto) {
        return ResponseEntity.ok(ingredientService.save(createDto));
    }

    @Operation(summary = "Delete an ingredient", description = "Deletes an ingredient by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
