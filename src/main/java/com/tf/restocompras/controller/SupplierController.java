package com.tf.restocompras.controller;

import com.tf.restocompras.model.supplier.SupplierCreateRequestDto;
import com.tf.restocompras.model.supplier.SupplierResponseDto;
import com.tf.restocompras.model.supplier.SupplierUpdateRequestDto;
import com.tf.restocompras.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier", description = "Endpoints for managing suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Get all suppliers", description = "Returns a list of all suppliers")
    @GetMapping
    public ResponseEntity<List<SupplierResponseDto>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @Operation(summary = "Get supplier by ID", description = "Returns a supplier by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @Operation(summary = "Create a new supplier", description = "Creates a new supplier")
    @PostMapping
    public ResponseEntity<SupplierResponseDto> createSupplier(@RequestBody SupplierCreateRequestDto supplierCreateRequestDto) {
        return ResponseEntity.ok(supplierService.createSupplier(supplierCreateRequestDto));
    }

    @Operation(summary = "Update a supplier", description = "Updates an existing supplier")
    @PutMapping
    public ResponseEntity<SupplierResponseDto> updateSupplier(@RequestBody SupplierUpdateRequestDto supplierUpdateRequestDto) {
        return ResponseEntity.ok(supplierService.updateSupplier(supplierUpdateRequestDto));
    }

    @Operation(summary = "Delete a supplier", description = "Deletes a supplier by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add user to supplier", description = "Adds a user to an existing supplier")
    @PutMapping("/{supplierId}/users/{userId}")
    public ResponseEntity<SupplierResponseDto> addUserToSupplier(
            @PathVariable Long supplierId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(supplierService.addUserToSupplier(supplierId, userId));
    }
}
