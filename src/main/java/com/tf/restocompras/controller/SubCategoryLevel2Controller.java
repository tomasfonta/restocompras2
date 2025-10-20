package com.tf.restocompras.controller;

import com.tf.restocompras.model.category.SubCategoryLevel2CreateRequestDto;
import com.tf.restocompras.model.category.SubCategoryLevel2ResponseDto;
import com.tf.restocompras.service.SubCategoryLevel2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories-level2")
@Tag(name = "SubCategoryLevel2", description = "Endpoints for managing subcategories level 2")
public class SubCategoryLevel2Controller {

    private final SubCategoryLevel2Service subCategoryLevel2Service;

    public SubCategoryLevel2Controller(SubCategoryLevel2Service subCategoryLevel2Service) {
        this.subCategoryLevel2Service = subCategoryLevel2Service;
    }

    @Operation(summary = "Get all subcategories level 2", description = "Returns a list of all subcategories level 2")
    @GetMapping
    public ResponseEntity<List<SubCategoryLevel2ResponseDto>> getAllSubCategoriesLevel2() {
        return ResponseEntity.ok(subCategoryLevel2Service.getAllSubCategoriesLevel2());
    }

    @Operation(summary = "Get subcategory level 2 by ID", description = "Returns a subcategory level 2 by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryLevel2ResponseDto> getSubCategoryLevel2ById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryLevel2Service.getSubCategoryLevel2ById(id));
    }

    @Operation(summary = "Create a new subcategory level 2", description = "Creates a new subcategory level 2")
    @PostMapping
    public ResponseEntity<SubCategoryLevel2ResponseDto> createSubCategoryLevel2(@RequestBody SubCategoryLevel2CreateRequestDto dto) {
        return ResponseEntity.ok(subCategoryLevel2Service.createSubCategoryLevel2(dto));
    }

    @Operation(summary = "Delete a subcategory level 2", description = "Deletes a subcategory level 2 by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategoryLevel2(@PathVariable Long id) {
        subCategoryLevel2Service.deleteSubCategoryLevel2(id);
        return ResponseEntity.noContent().build();
    }
}

