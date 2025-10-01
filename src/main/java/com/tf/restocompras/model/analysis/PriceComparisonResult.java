package com.tf.restocompras.model.analysis;

import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import lombok.Builder;

@Builder
public record PriceComparisonResult(
        Item item,
        Ingredient ingredient,
        Double priceDifferenceInPercentage,
        Double monthlySavings
) {
}