package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.ingredient.IngredientRequestDto;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.recipe.RecipeCreateRequestDto;
import com.tf.restocompras.model.recipe.RecipeResponseDto;
import com.tf.restocompras.model.recipe.RecipeUpdateRequestDto;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.IngredientRepository;
import com.tf.restocompras.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         RecipeMapper recipeMapper, ProductRepository productRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.productRepository = productRepository;
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
                newIngredient.setSupplier(ingredientDto.supplier());
                newIngredient.setRecipe(recipe);
                newIngredient.setPrice(ingredientDto.price());

                if (ingredientDto.productId() != null) {
                    Product product = productRepository.findById(ingredientDto.productId())
                            .orElseThrow(() -> new NotFoundException("Product not found with id " + ingredientDto.productId()));
                    newIngredient.setProduct(product);
                }

                ingredients.add(newIngredient);
            }
        }

        recipe.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(recipe);

        return recipeMapper.mapEntityToDto(saved);
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

    @Transactional
    public RecipeResponseDto update(RecipeUpdateRequestDto recipeUpdateRequestDto) {
        if (recipeUpdateRequestDto.id() == null || !recipeRepository.existsById(recipeUpdateRequestDto.id())) {
            throw new NotFoundException("Recipe not found with id " + recipeUpdateRequestDto.id());
        }

        // Get the existing recipe from the database
        Recipe existingRecipe = recipeRepository.findById(recipeUpdateRequestDto.id())
                .orElseThrow(() -> new NotFoundException("Recipe not found with id " + recipeUpdateRequestDto.id()));

        // get removed ingredients
        List<Ingredient> ingredientsToRemove = new ArrayList<>();
        for (Ingredient existingIngredient : existingRecipe.getIngredients()) {
            boolean found = false;
            if (recipeUpdateRequestDto.ingredients() != null) {
                for (IngredientRequestDto updatedIngredientDto : recipeUpdateRequestDto.ingredients()) {
                    if (updatedIngredientDto.id() != null && updatedIngredientDto.id().equals(existingIngredient.getId())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                ingredientsToRemove.add(existingIngredient);
            }
        }
        // Remove ingredients that are no longer present
        for (Ingredient ingredientToRemove : ingredientsToRemove) {
            existingRecipe.getIngredients().remove(ingredientToRemove);
            ingredientRepository.delete(ingredientToRemove);
        }

        // Update basic recipe details
        existingRecipe.setName(recipeUpdateRequestDto.name());
        existingRecipe.setDescription(recipeUpdateRequestDto.description());
        existingRecipe.setPrice(BigDecimal.valueOf(recipeUpdateRequestDto.price()));
        existingRecipe.setInstructions(recipeUpdateRequestDto.instructions());
        existingRecipe.setMonthlyServings(recipeUpdateRequestDto.monthlyServings());
        existingRecipe.setCookingTimeInMinutes(recipeUpdateRequestDto.cookingTimeInMinutes());

        if (recipeUpdateRequestDto.ingredients() != null) {
            List<Ingredient> updatedIngredients = new ArrayList<>();
            for (IngredientRequestDto ingredientDto : recipeUpdateRequestDto.ingredients()) {
                Ingredient ingredient;
                if (ingredientDto.id() != null) {
                    // Existing ingredient, update it
                    ingredient = ingredientRepository.findById(ingredientDto.id())
                            .orElseThrow(() -> new NotFoundException("Ingredient not found with id " + ingredientDto.id()));
                    ingredient.setName(ingredientDto.name());
                    ingredient.setQuantity(ingredientDto.quantity());
                    ingredient.setUnit(Unit.valueOf(ingredientDto.unit()));
                    ingredient.setPrice(ingredientDto.price());
                    ingredient.setSupplier(ingredientDto.supplier());
                    if (ingredientDto.productId() != null) {
                        Product product = productRepository.findById(ingredientDto.productId())
                                .orElseThrow(() -> new NotFoundException("Product not found with id " + ingredientDto.productId()));
                        ingredient.setProduct(product);
                    } else {
                        ingredient.setProduct(null); // Clear product if productId is null
                    }
                } else {
                    // New ingredient, create it
                    ingredient = new Ingredient();
                    ingredient.setName(ingredientDto.name());
                    ingredient.setQuantity(ingredientDto.quantity());
                    ingredient.setUnit(Unit.valueOf(ingredientDto.unit()));
                    ingredient.setPrice(ingredientDto.price());
                    ingredient.setSupplier(ingredientDto.supplier());
                    ingredient.setRecipe(existingRecipe); // Set the recipe for the new ingredient
                    if (ingredientDto.productId() != null) {
                        Product product = productRepository.findById(ingredientDto.productId())
                                .orElseThrow(() -> new NotFoundException("Product not found with id " + ingredientDto.productId()));
                        ingredient.setProduct(product);
                    }
                }
                updatedIngredients.add(ingredient);
            }
            existingRecipe.setIngredients(updatedIngredients);
        }
        return recipeMapper.mapEntityToDto(recipeRepository.save(existingRecipe));

    }
}
