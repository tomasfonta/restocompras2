package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.bundle.Bundle;
import com.tf.restocompras.model.bundle.BundleCreateRequestDto;
import com.tf.restocompras.model.bundle.BundleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {ItemMapper.class, SupplierMapper.class})
public interface BundleMapper {

    BundleResponseDto mapEntityToDto(Bundle bundle);

    Bundle mapDtoToEntity(BundleCreateRequestDto bundleCreateRequestDto);

}
