package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.category.CategoryCreateRequestDto;
import com.tf.restocompras.model.category.CategoryResponseDto;
import com.tf.restocompras.model.category.CategoryUpdateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SubCategoryLevel1Mapper.class})
public interface CategoryMapper {

    Category mapDtoToEntity(CategoryUpdateRequestDto categoryUpdateRequestDto);

    CategoryResponseDto mapEntityToDto(Category parentCategory);

    Category mapDtoToEntity(CategoryCreateRequestDto categoryCreateRequestDto);
}
