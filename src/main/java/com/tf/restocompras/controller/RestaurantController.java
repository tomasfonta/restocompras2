package com.tf.restocompras.controller;

import com.tf.restocompras.model.restaurant.RestaurantCreateRequestDto;
import com.tf.restocompras.model.restaurant.RestaurantResponseDto;
import com.tf.restocompras.model.restaurant.RestaurantUpdateRequestDto;
import com.tf.restocompras.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant", description = "Endpoints for managing restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Get all restaurants", description = "Returns a list of all restaurants")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @Operation(summary = "Get restaurant by ID", description = "Returns a restaurant by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant")
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody RestaurantCreateRequestDto restaurantCreateRequestDto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantCreateRequestDto));
    }

    @Operation(summary = "Update a restaurant", description = "Updates an existing restaurant")
    @PutMapping
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@RequestBody RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantUpdateRequestDto));
    }

    @Operation(summary = "Delete a restaurant", description = "Deletes a restaurant by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add user to restaurant", description = "Adds a user to an existing restaurant")
    @PutMapping("/{restaurantId}/users/{userId}")
    public ResponseEntity<RestaurantResponseDto> addUserToRestaurant(
            @PathVariable Long restaurantId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(restaurantService.addUserToRestaurant(restaurantId, userId));
    }
}
