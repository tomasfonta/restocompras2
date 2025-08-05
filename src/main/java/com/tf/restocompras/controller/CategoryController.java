package com.tf.restocompras.controller;

import com.tf.restocompras.model.category.CategoryCreateRequestDto;
import com.tf.restocompras.model.category.CategoryResponseDto;
import com.tf.restocompras.model.category.CategoryUpdateRequestDto;
import com.tf.restocompras.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Get category by ID", description = "Returns a category by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Create a new category", description = "Creates a new category")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryCreateRequestDto category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @Operation(summary = "Update a category", description = "Updates an existing category")
    @PutMapping("/")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryUpdateRequestDto));
    }

    @Operation(summary = "Delete a category", description = "Deletes a category by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
