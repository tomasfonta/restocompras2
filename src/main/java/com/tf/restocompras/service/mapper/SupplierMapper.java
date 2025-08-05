package com.tf.restocompras.service.mapper;


import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.supplier.SupplierCreateRequestDto;
import com.tf.restocompras.model.supplier.SupplierResponseDto;
import com.tf.restocompras.model.supplier.SupplierUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SupplierMapper {

    SupplierResponseDto mapEntityToDto(Supplier supplier);

    List<SupplierResponseDto> mapEntitiesToDtos(List<Supplier> suppliers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Supplier mapCreateDtoToEntity(SupplierCreateRequestDto supplierCreateRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void mapUpdateDtoToEntity(SupplierUpdateRequestDto supplierUpdateRequestDto, @MappingTarget Supplier supplier);
}
