package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.recipe.RecipeCreateRequestDto;
import com.tf.restocompras.model.recipe.RecipeResponseDto;
import com.tf.restocompras.repository.IngredientRepository;
import com.tf.restocompras.repository.RecipeRepository;
import com.tf.restocompras.service.mapper.RecipeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeResponseDto> findAll() {
        return recipeMapper.mapEntitiesToDtos(recipeRepository.findAll());
    }

    public RecipeResponseDto findById(Long id) {
        return recipeMapper.mapEntityToDto(recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found with id " + id)));
    }

    public RecipeResponseDto save(RecipeCreateRequestDto recipeCreateRequestDto) {
        Recipe recipe = recipeMapper.mapCreateDtoToEntity(recipeCreateRequestDto);
        return recipeMapper.mapEntityToDto(recipeRepository.save(recipe));
    }

    @Transactional
    public RecipeResponseDto addIngredientToRecipe(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found with id " + recipeId));

        var ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NotFoundException("Ingredient not found with id " + ingredientId));

        recipe.getIngredients().add(ingredient);

        return recipeMapper.mapEntityToDto(recipeRepository.save(recipe));
    }

    public void deleteById(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException("Recipe not found with id " + id);
        }
        recipeRepository.deleteById(id);
    }
}
