package com.tf.restocompras.model.analysis;

import com.tf.restocompras.model.ingredient.IngredientResponseDto;
import com.tf.restocompras.model.item.ItemResponseDto;
import lombok.Builder;

@Builder
public record PriceComparisonResultResponseDto(
        ItemResponseDto item,
        IngredientResponseDto ingredient,
        Double priceDifferenceInPercentage,
        Double monthlySavings
) {
}