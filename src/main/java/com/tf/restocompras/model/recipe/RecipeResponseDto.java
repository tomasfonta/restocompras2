package com.tf.restocompras.model.recipe;

import com.tf.restocompras.model.ingredient.IngredientResponseDto;

import java.util.List;

public record RecipeResponseDto(
        Long id,
        String name,
        String description,
        Double price,
        String instructions,
        Integer monthlyServings,
        Long restaurantId,
        Integer cookingTimeInMinutes,
        List<IngredientResponseDto> ingredients
) {
}
