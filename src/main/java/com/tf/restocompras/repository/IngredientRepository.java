package com.tf.restocompras.repository;

import com.tf.restocompras.model.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Returns all ingredients belonging to a recipe
    List<Ingredient> findByRecipeId(Long recipeId);

    List<Ingredient> findAllByRecipeId(Long recipeId);
}
