package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.item.ItemCreateRequestDto;
import com.tf.restocompras.model.item.ItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {ProductMapper.class, SupplierMapper.class})
public interface ItemMapper {

    ItemResponseDto mapEntityToDto(Item item);

    Item mapDtoToEntity(ItemCreateRequestDto itemCreateRequestDto);

}
