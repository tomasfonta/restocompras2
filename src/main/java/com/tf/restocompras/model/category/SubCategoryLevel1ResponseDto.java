package com.tf.restocompras.model.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SubCategoryLevel1ResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        List<SubCategoryLevel2ResponseDto> subCategoriesLevel2,
        Long categoryId
) {
}

