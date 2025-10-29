package com.tf.restocompras.controller;

import com.tf.restocompras.model.bundle.BundleCreateRequestDto;
import com.tf.restocompras.model.bundle.BundleResponseDto;
import com.tf.restocompras.model.bundle.BundleUpdateRequestDto;
import com.tf.restocompras.service.BundleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bundle")
@Tag(name = "Bundle", description = "Endpoints for managing Bundles")
public class BundleController {

    private final BundleService bundleService;

    public BundleController(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    @Operation(summary = "Get all Bundles", description = "Returns a list of all Bundles")
    @GetMapping
    public ResponseEntity<List<BundleResponseDto>> getAllBundles() {
        return ResponseEntity.ok(bundleService.findAll());
    }

    @Operation(summary = "Get Bundle by ID", description = "Returns a Bundle by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BundleResponseDto> getBundleById(@PathVariable Long id) {
        return ResponseEntity.ok(bundleService.findById(id));
    }

    @Operation(summary = "Create a new Bundle", description = "Creates a new Bundle")
    @PostMapping
    public ResponseEntity<BundleResponseDto> createBundle(@RequestBody BundleCreateRequestDto bundleCreateRequestDto) {
        return ResponseEntity.ok(bundleService.save(bundleCreateRequestDto));
    }

    @Operation(summary = "Update a Bundle", description = "Updates an existing Bundle")
    @PutMapping
    public ResponseEntity<BundleResponseDto> updateBundle(@RequestBody BundleUpdateRequestDto bundleUpdateRequestDto) {
        return ResponseEntity.ok(bundleService.update(bundleUpdateRequestDto));
    }

    @Operation(summary = "Delete a Bundle", description = "Deletes a Bundle by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBundle(@PathVariable Long id) {
        bundleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Bundles by Item ID", description = "Returns bundles for a given item ID")
    @GetMapping("/items/{itemId}")
    public ResponseEntity<List<BundleResponseDto>> getBundlesByItemId(@PathVariable Long itemId) {
        return ResponseEntity.ok(bundleService.findByItemId(itemId));
    }

    @Operation(summary = "Get Bundles by Supplier ID", description = "Returns bundles for a given supplier ID")
    @GetMapping("/suppliers/{supplierId}")
    public ResponseEntity<List<BundleResponseDto>> getBundlesBySupplierId(@PathVariable Long supplierId) {
        return ResponseEntity.ok(bundleService.findBySupplierId(supplierId));
    }
}
