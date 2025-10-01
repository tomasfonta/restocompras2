package com.tf.restocompras.model.analysis;

import lombok.Builder;

import java.util.List;

@Builder
public record PriceComparisonResponseDto(
        Double totalMonthlySavings,
        List<PriceComparisonResultResponseDto> results
) {
}
