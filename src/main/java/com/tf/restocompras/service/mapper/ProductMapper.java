package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.model.product.ProductUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring" , nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "userId", source = "user.id")
    ProductResponseDto mapEntityToDto(Product product);


    List<ProductResponseDto> mapEntityListToDtoList(List<Product> products);
    @Mapping(source = "categoryName", target = "category.name")
    @Mapping(source = "userId", target = "user.id")
    Product mapDtoToEntity(ProductCreateRequestDto productCreateRequestDto);

    Product mapDtoToEntity(ProductUpdateRequestDto productCreateRequestDto);
}
