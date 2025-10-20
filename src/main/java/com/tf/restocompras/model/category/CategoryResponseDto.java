package com.tf.restocompras.model.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        List<SubCategoryLevel1ResponseDto> subCategoriesLevel1
) {

}
