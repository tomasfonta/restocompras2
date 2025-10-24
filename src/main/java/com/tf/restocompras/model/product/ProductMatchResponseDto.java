package com.tf.restocompras.model.product;

public record ProductMatchResponseDto(
        Long productId,
        String productName,
        String categoryName,
        String subCategoryLevel1Name,
        String subCategoryLevel2Name,
        Double matchScore
) {
}

