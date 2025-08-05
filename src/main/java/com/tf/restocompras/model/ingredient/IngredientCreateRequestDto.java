package com.tf.restocompras.model.ingredient;

import java.math.BigDecimal;

public record IngredientCreateRequestDto(
        String name,
        BigDecimal quantity,
        String dimension,
        BigDecimal cost,
        String supplier,
        Long productId,
        Long recipeId
) {
}
