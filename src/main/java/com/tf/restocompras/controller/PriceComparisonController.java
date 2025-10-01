package com.tf.restocompras.controller;

import com.tf.restocompras.model.analysis.PriceComparisonResponseDto;
import com.tf.restocompras.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@Tag(name = "analysis", description = "Endpoints for costs analysis")
public class PriceComparisonController {

    private final AnalysisService analysisService;

    public PriceComparisonController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Operation(summary = "Find alternative products ", description = "Find alternative products based on cost analysis")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<PriceComparisonResponseDto> performAnalysis(@PathVariable Long restaurantId) {
        PriceComparisonResponseDto priceComparisonResponseDto = analysisService.performIngredientsPriceComparison(restaurantId);
        return ResponseEntity.ok(priceComparisonResponseDto);
    }
}
