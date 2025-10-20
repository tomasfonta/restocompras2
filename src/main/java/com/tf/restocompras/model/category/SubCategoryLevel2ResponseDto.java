package com.tf.restocompras.model.category;

import com.tf.restocompras.model.product.ProductResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SubCategoryLevel2ResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotNull Long subCategoryLevel1Id,
        List<ProductResponseDto> products
) {
}

