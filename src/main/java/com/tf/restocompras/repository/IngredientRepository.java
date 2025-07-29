package com.tf.restocompras.repository;

import com.tf.restocompras.model.recipe.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

