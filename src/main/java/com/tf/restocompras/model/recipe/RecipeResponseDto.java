package com.tf.restocompras.model.recipe;

import com.tf.restocompras.model.ingredient.IngredientResponseDto;

import java.util.List;

public record RecipeResponseDto(
        String name,
        String description,
        Double price,
        String instructions,
        Integer cookingTimeInMinutes,
        Integer monthlyServings,
        Long restaurantId,
        List<IngredientResponseDto> ingredients
) {
}
