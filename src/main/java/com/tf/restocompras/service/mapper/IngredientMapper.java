package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.ingredient.IngredientCreateRequestDto;
import com.tf.restocompras.model.ingredient.IngredientResponseDto;
import com.tf.restocompras.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "productId", source = "product.id")
    IngredientResponseDto mapEntityToDto(Ingredient ingredient);

    List<IngredientResponseDto> mapEntitiesToDtos(List<Ingredient> ingredients);

    @Mapping(target = "recipe.id", source = "recipeId")
    @Mapping(target = "product", expression = "java(productFromId(ingredientCreateRequestDto.productId()))")
    Ingredient mapCreateDtoToEntity(IngredientCreateRequestDto ingredientCreateRequestDto);

    default Product productFromId(Long id) {
        if (id == null) return null;
        return Product.builder().id(id).build();
    }
}
