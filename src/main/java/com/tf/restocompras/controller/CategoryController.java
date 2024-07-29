package com.tf.restocompras.controller;

import com.tf.restocompras.model.category.CategoryCreateRequestDto;
import com.tf.restocompras.model.category.CategoryResponseDto;
import com.tf.restocompras.model.category.CategoryUpdateRequestDto;
import com.tf.restocompras.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryCreateRequestDto category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryUpdateRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
    }
}

