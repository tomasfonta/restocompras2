package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.ingredient.IngredientCreateRequestDto;
import com.tf.restocompras.model.ingredient.IngredientResponseDto;
import com.tf.restocompras.model.ingredient.IngredientUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "productId", source = "product.id")
    IngredientResponseDto mapEntityToDto(Ingredient ingredient);

    List<IngredientResponseDto> mapEntitiesToDtos(List<Ingredient> ingredients);

    @Mapping(target = "product.id", source = "productId")
    Ingredient mapCreateDtoToEntity(IngredientCreateRequestDto ingredientCreateRequestDto);

    @Mapping(target = "product.id", source = "productId")
    Ingredient mapUpdateDtoToEntity(IngredientUpdateRequestDto dto);
}
