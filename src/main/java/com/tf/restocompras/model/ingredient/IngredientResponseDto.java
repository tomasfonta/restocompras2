package com.tf.restocompras.model.ingredient;

public record IngredientResponseDto(
        Long id,
        String name,
        Double quantity,
        String unit,
        Double price,
        Long recipeId,
        Long productId
) {
}
