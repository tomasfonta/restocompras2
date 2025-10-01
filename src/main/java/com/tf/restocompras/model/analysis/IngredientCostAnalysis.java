package com.tf.restocompras.model.analysis;

import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.recipe.Recipe;

import java.util.List;

public class IngredientCostAnalysis {

    Ingredient originalIngredient;
    Ingredient alternativeIngredient;
    Double costDifferenceInPercentage;
    Double monthlySavings;
    List<Recipe> recipes;

}
