package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.category.SubCategoryLevel2;
import com.tf.restocompras.model.category.SubCategoryLevel2ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface SubCategoryLevel2Mapper {
    @Mapping(target = "subCategoryLevel1Id", source = "subCategoryLevel1.id")
    SubCategoryLevel2ResponseDto mapEntityToDto(SubCategoryLevel2 entity);
}

