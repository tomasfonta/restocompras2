package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.recipe.RecipeCreateRequestDto;
import com.tf.restocompras.model.recipe.RecipeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface RecipeMapper {

    RecipeResponseDto mapEntityToDto(Recipe recipe);

    List<RecipeResponseDto> mapEntitiesToDtos(List<Recipe> recipes);
    
    @Mapping(target = "restaurant.id", source = "restaurantId")
    Recipe mapCreateDtoToEntity(RecipeCreateRequestDto recipeCreateRequestDto);
}
