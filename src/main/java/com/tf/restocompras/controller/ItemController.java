package com.tf.restocompras.controller;


import com.tf.restocompras.model.item.ItemCreateRequestDto;
import com.tf.restocompras.model.item.ItemResponseDto;
import com.tf.restocompras.model.item.ItemUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tf.restocompras.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@Tag(name = "Item", description = "Endpoints for managing Items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Get all Items", description = "Returns a list of all Items")
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @Operation(summary = "Get Item by ID", description = "Returns a Item by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @Operation(summary = "Create a new Item", description = "Creates a new Item")
    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemCreateRequestDto itemCreateRequestDto) {
        return ResponseEntity.ok(itemService.save(itemCreateRequestDto));
    }

    @Operation(summary = "Update a Item", description = "Updates an existing Item")
    @PutMapping
    public ResponseEntity<ItemResponseDto> updateItem(@RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
        return ResponseEntity.ok(itemService.update(itemUpdateRequestDto));
    }

    @Operation(summary = "Delete a Item", description = "Deletes a Item by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Items by Suplier ID", description = "Returns items for a given supplier ID")
    @GetMapping("/users/{supplierId}")
    public ResponseEntity<List<ItemResponseDto>> getItemsByUserId(@PathVariable Long supplierId) {
        return ResponseEntity.ok(itemService.findBySupplierId(supplierId));
    }

}