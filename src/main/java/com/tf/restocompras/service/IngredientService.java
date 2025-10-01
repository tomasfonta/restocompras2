package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.ingredient.IngredientCreateRequestDto;
import com.tf.restocompras.model.ingredient.IngredientResponseDto;
import com.tf.restocompras.model.ingredient.IngredientUpdateRequestDto;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.repository.IngredientRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.RecipeRepository;
import com.tf.restocompras.service.mapper.IngredientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository,
                             ProductRepository productRepository,
                             IngredientMapper ingredientMapper, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
        this.ingredientMapper = ingredientMapper;
        this.recipeRepository = recipeRepository;
    }

    public List<IngredientResponseDto> findAll() {
        return ingredientMapper.mapEntitiesToDtos(ingredientRepository.findAll());
    }

    public IngredientResponseDto findById(Long id) {
        return ingredientMapper.mapEntityToDto(ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found with id " + id)));
    }

    @Transactional
    public IngredientResponseDto save(IngredientCreateRequestDto createDto) {
        if (createDto.productId() != null) {
            productRepository.findById(createDto.productId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id " + createDto.productId()));
        }
        Ingredient ingredient = ingredientMapper.mapCreateDtoToEntity(createDto);
        return ingredientMapper.mapEntityToDto(ingredientRepository.save(ingredient));
    }

    @Transactional
    public IngredientResponseDto save(IngredientUpdateRequestDto dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product not found with id " + dto.productId()));

        Recipe recipe = recipeRepository.findById(dto.recipeId())
                .orElseThrow(() -> new NotFoundException("Recipe not found with id " + dto.recipeId()));

        Ingredient ingredient = ingredientMapper.mapUpdateDtoToEntity(dto);
        ingredient.setRecipe(recipe);
        ingredient.setProduct(product);

        return ingredientMapper.mapEntityToDto(ingredientRepository.save(ingredient));
    }

    public void deleteById(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException("Ingredient not found with id " + id);
        }
        ingredientRepository.deleteById(id);
    }

//    public Ingredient findCheapestByProductId(Long productId) {
//
//        return ingredientRepository.findByProduct(productId)
//                .orElseThrow( public Ingredient findCheapestByProductId(Long productId) {
////
////        return ingredientRepository.findByProduct(productId)
////                .orElseThrow(() -> new NotFoundException("No ingredients found for product id " + productId));
////    }() -> new NotFoundException("No ingredients found for product id " + productId));
//    }
}
