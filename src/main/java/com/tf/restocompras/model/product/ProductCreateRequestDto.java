package com.tf.restocompras.model.product;

public record ProductCreateRequestDto(
        String name,
        Long subCategoryLevel2Id
) {
}

