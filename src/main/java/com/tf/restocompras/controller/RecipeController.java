package com.tf.restocompras.controller;

import com.tf.restocompras.model.recipe.RecipeCreateRequestDto;
import com.tf.restocompras.model.recipe.RecipeResponseDto;
import com.tf.restocompras.model.recipe.RecipeUpdateRequestDto;
import com.tf.restocompras.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipe", description = "Endpoints for managing recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Get all recipes", description = "Returns a list of all recipes")
    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.findAll());
    }

    @Operation(summary = "Get recipe by ID", description = "Returns a recipe by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @Operation(summary = "Create a new recipe", description = "Creates a new recipe without ingredients")
    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@RequestBody RecipeCreateRequestDto recipeCreateRequestDto) {
        return ResponseEntity.ok(recipeService.save(recipeCreateRequestDto));
    }

    @Operation(summary = "Update recipe", description = "Update without ingredients")
    @PutMapping
    public ResponseEntity<RecipeResponseDto> updateRecipe(@RequestBody RecipeUpdateRequestDto recipeCreateRequestDto) {
        return ResponseEntity.ok(recipeService.update(recipeCreateRequestDto));
    }

    @Operation(summary = "Add ingredient to recipe", description = "Adds an ingredient to an existing recipe")
    @PutMapping("/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<RecipeResponseDto> addIngredientToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(recipeService.addIngredientToRecipe(recipeId, ingredientId));
    }

    @Operation(summary = "Delete a recipe", description = "Deletes a recipe by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Recipes by restaurant ID", description = "Returns a recipe by restaurant ID")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipesByRestaurantId(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getByRestaurantId(id));
    }
}
