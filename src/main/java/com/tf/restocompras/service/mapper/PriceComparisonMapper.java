package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.analysis.PriceComparison;
import com.tf.restocompras.model.analysis.PriceComparisonResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {PriceComparisonResultMapper.class})
public interface PriceComparisonMapper {

    PriceComparisonResponseDto mapEntityToDto(PriceComparison priceComparisonResult);

}
