package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.item.ItemCreateRequestDto;
import com.tf.restocompras.model.item.ItemResponseDto;
import com.tf.restocompras.model.item.ItemUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring" , nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface ItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "supplierId", source = "supplier.id")
    ItemResponseDto mapEntityToDto(Item item);


    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "supplier.id", source = "supplierId")
    Item mapDtoToEntity(ItemCreateRequestDto itemCreateRequestDto);

}
