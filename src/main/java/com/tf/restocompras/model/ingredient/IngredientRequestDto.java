package com.tf.restocompras.model.ingredient;

import java.math.BigDecimal;

public record IngredientRequestDto(
        Long id,
        String name,
        BigDecimal quantity,
        String unit,
        BigDecimal cost,
        String supplier,
        Long productId,
        Long recipeId
) {
}
