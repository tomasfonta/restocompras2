package com.tf.restocompras.controller;

import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductMatchResponseDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Products", description = "Endpoints for managing products")
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductCreateRequestDto requestDto) {
        return ResponseEntity.ok(productService.save(requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/best-match")
    @Operation(summary = "Find best matching product",
            description = "Searches for the best matching product based on name or description, considering category hierarchy")
    public ResponseEntity<ProductMatchResponseDto> findBestMatch(@RequestParam String query) {
        return ResponseEntity.ok(productService.findBestMatch(query));
    }
}


