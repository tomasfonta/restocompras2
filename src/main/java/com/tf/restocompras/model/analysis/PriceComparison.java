package com.tf.restocompras.model.analysis;

import lombok.Builder;

import java.util.List;

@Builder
public record PriceComparison(
        Double totalMonthlySavings,
        List<PriceComparisonResult> results
) {
}
