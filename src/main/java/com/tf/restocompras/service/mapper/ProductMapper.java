package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring" , nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface ProductMapper {


    ProductResponseDto mapEntityToDto(Product product);


    List<ProductResponseDto> mapEntityListToDtoList(List<Product> products);

}
