package com.tf.restocompras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/engineering")
@Tag(name = "Engineering", description = "Engineering endpoints for health checks and monitoring")
public class EngineeringController {

    @Operation(summary = "Ping endpoint", description = "Simple ping endpoint that returns 'pong'")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
