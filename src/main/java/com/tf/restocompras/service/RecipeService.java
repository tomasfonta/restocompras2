package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.ingredient.IngredientRequestDto;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.recipe.RecipeCreateRequestDto;
import com.tf.restocompras.model.recipe.RecipeResponseDto;
import com.tf.restocompras.model.recipe.RecipeUpdateRequestDto;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.IngredientRepository;
import com.tf.restocompras.repository.RecipeRepository;
import com.tf.restocompras.service.mapper.RecipeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Transactional
    public RecipeResponseDto save(RecipeCreateRequestDto recipeCreateRequestDto) {
        Recipe recipe = recipeMapper.mapCreateDtoToEntity(recipeCreateRequestDto);


        List<Ingredient> ingredients = new ArrayList<>();

        // If ingredients are provided, process them
        if (recipeCreateRequestDto.ingredients() != null && !recipeCreateRequestDto.ingredients().isEmpty()) {

            for (IngredientRequestDto ingredientDto : recipeCreateRequestDto.ingredients()) {

                Ingredient newIngredient = new Ingredient();
                newIngredient.setName(ingredientDto.name());
                newIngredient.setQuantity(ingredientDto.quantity());
                newIngredient.setUnit(Unit.valueOf(ingredientDto.unit()));
                ingredientRepository.save(newIngredient);

                ingredients.add(newIngredient);
            }
        }

        recipe.setIngredients(ingredients);
        Recipe savedRecipe = recipeRepository.save(recipe);

        return recipeMapper.mapEntityToDto(savedRecipe);
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

    public List<RecipeResponseDto> getByRestaurantId(Long id) {
        return recipeMapper.mapEntitiesToDtos(recipeRepository.findAllByRestaurantId(id));
    }

    public RecipeResponseDto update(RecipeUpdateRequestDto recipeUpdateRequestDto) {
        if (recipeUpdateRequestDto.id() == null || !recipeRepository.existsById(recipeUpdateRequestDto.id())) {
            throw new NotFoundException("Recipe not found with id " + recipeUpdateRequestDto.id());
        }
        Recipe recipe = recipeMapper.mapUpdateDtoToEntity(recipeUpdateRequestDto);

        recipe.setDescription(recipeUpdateRequestDto.description());
        recipe.setName(recipeUpdateRequestDto.name());
        recipe.setPrice(BigDecimal.valueOf(recipeUpdateRequestDto.price()));
        recipe.setInstructions(recipeUpdateRequestDto.instructions());
        recipe.setMonthlyServings(recipeUpdateRequestDto.monthlyServings());
        recipe.setCookingTimeInMinutes(recipeUpdateRequestDto.cookingTimeInMinutes());

        return recipeMapper.mapEntityToDto(recipeRepository.save(recipe));
    }
}
