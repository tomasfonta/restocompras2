package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.analysis.PriceComparisonResult;
import com.tf.restocompras.model.analysis.PriceComparisonResultResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {ItemMapper.class, IngredientMapper.class})
public interface PriceComparisonResultMapper {

    PriceComparisonResultResponseDto mapEntityToDto(PriceComparisonResult entity);

}
