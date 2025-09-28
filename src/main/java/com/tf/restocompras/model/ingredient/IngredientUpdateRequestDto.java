package com.tf.restocompras.model.ingredient;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record IngredientUpdateRequestDto(
        Long id,
        String name,
        BigDecimal quantity,
        String unit,
        BigDecimal price,
        String supplier,
        Long productId,
        Long recipeId
) {
}
