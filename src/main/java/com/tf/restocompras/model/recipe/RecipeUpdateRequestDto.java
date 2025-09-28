package com.tf.restocompras.model.recipe;

import com.tf.restocompras.model.ingredient.IngredientRequestDto;

import java.util.List;

public record RecipeUpdateRequestDto(
        Long id,
        String name,
        String description,
        Double price,
        String instructions,
        Integer monthlyServings,
        Integer cookingTimeInMinutes,
        Long restaurantId,
        List<IngredientRequestDto> ingredients) {
}
