package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.category.CategoryCreateRequestDto;
import com.tf.restocompras.model.category.CategoryResponseDto;
import com.tf.restocompras.model.category.CategoryUpdateRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {ProductMapper.class})
public interface CategoryMapper {


    Category mapDtoToEntity(CategoryUpdateRequestDto categoryUpdateRequestDto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    CategoryResponseDto mapEntityToDto(Category category);


    Category mapDtoToEntity(CategoryCreateRequestDto categoryCreateRequestDto);
}
