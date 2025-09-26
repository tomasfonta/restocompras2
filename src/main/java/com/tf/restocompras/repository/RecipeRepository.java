package com.tf.restocompras.repository;

import com.tf.restocompras.model.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByRestaurantId(Long restaurantId);
}

