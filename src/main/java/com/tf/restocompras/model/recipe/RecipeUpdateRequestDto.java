package com.tf.restocompras.model.recipe;

public record RecipeUpdateRequestDto(
        Long id,
        String name,
        String description,
        Double price,
        String instructions,
        Integer monthlyServings,
        Integer cookingTimeInMinutes,
        Long restaurantId) {
}
