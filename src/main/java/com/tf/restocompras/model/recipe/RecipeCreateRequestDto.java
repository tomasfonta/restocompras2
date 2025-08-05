package com.tf.restocompras.model.recipe;

public record RecipeCreateRequestDto(
        String name,
        String description,
        Double price,
        String instructions,
        Integer cookingTimeInMinutes,
        Integer monthlyServings,
        Long restaurantId) {
}
