package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring" , nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDto mapEntityToDto(Product product);


    @Mapping(target = "category.id", source = "categoryId")
    Product mapDtoToEntity(ProductCreateRequestDto dto);

    @Mapping(target = "categoryName", source = "category.name")
    List<ProductResponseDto> mapEntitiesToDtos(List<Product> products) ;
}

