package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.category.SubCategoryLevel1;
import com.tf.restocompras.model.category.SubCategoryLevel1ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubCategoryLevel2Mapper.class})
public interface SubCategoryLevel1Mapper {
    @Mapping(target = "categoryId", source = "category.id")
    SubCategoryLevel1ResponseDto mapEntityToDto(SubCategoryLevel1 entity);
}
