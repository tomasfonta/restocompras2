package com.tf.restocompras.controller;

import com.tf.restocompras.model.category.SubCategoryLevel1CreateRequestDto;
import com.tf.restocompras.model.category.SubCategoryLevel1ResponseDto;
import com.tf.restocompras.service.SubCategoryLevel1Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories-level1")
@Tag(name = "SubCategoryLevel1", description = "Endpoints for managing subcategories level 1")
public class SubCategoryLevel1Controller {

    private final SubCategoryLevel1Service subCategoryLevel1Service;

    public SubCategoryLevel1Controller(SubCategoryLevel1Service subCategoryLevel1Service) {
        this.subCategoryLevel1Service = subCategoryLevel1Service;
    }

    @Operation(summary = "Get all subcategories level 1", description = "Returns a list of all subcategories level 1")
    @GetMapping
    public ResponseEntity<List<SubCategoryLevel1ResponseDto>> getAllSubCategoriesLevel1() {
        return ResponseEntity.ok(subCategoryLevel1Service.getAllSubCategoriesLevel1());
    }

    @Operation(summary = "Get subcategory level 1 by ID", description = "Returns a subcategory level 1 by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryLevel1ResponseDto> getSubCategoryLevel1ById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryLevel1Service.getSubCategoryLevel1ById(id));
    }

    @Operation(summary = "Create a new subcategory level 1", description = "Creates a new subcategory level 1")
    @PostMapping
    public ResponseEntity<SubCategoryLevel1ResponseDto> createSubCategoryLevel1(@RequestBody SubCategoryLevel1CreateRequestDto dto) {
        return ResponseEntity.ok(subCategoryLevel1Service.createSubCategoryLevel1(dto));
    }

    @Operation(summary = "Delete a subcategory level 1", description = "Deletes a subcategory level 1 by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategoryLevel1(@PathVariable Long id) {
        subCategoryLevel1Service.deleteSubCategoryLevel1(id);
        return ResponseEntity.noContent().build();
    }
}

