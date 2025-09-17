package com.tf.restocompras.controller;

import com.tf.restocompras.model.user.UserCreateRequestDto;
import com.tf.restocompras.model.user.UserResponseDto;
import com.tf.restocompras.model.user.UserUpdateRequestDto;
import com.tf.restocompras.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        var userResponseDto = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Get user by Email", description = "Returns a user by their email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        var userResponseDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Get user by username", description = "Returns a user by their username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.ok(userService.saveUser(userCreateRequestDto));
    }

    @Operation(summary = "Update a user", description = "Updates an existing user")
    @PutMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.ok(userService.updateUser(userUpdateRequestDto));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}