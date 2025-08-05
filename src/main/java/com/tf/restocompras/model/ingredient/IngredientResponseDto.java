package com.tf.restocompras.model.ingredient;

public record IngredientResponseDto(
        Long id,
        String name,
        Double quantity,
        String dimension,
        Double cost,
        String supplier,
        Long productId,
        Long recipeId
) {
}
