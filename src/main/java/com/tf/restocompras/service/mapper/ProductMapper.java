package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProductMapper {

    @Mapping(target = "subCategoryLevel2Id", source = "subCategoryLevel2.id")
    ProductResponseDto mapEntityToDto(Product product);

    @Mapping(target = "subCategoryLevel2.id", source = "subCategoryLevel2Id")
    Product mapDtoToEntity(ProductCreateRequestDto dto);

    List<ProductResponseDto> mapEntitiesToDtos(List<Product> products);
}
