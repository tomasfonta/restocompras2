package com.tf.restocompras.model.supplier;

public record SupplierResponseDto(
        Long id,
        String name,
        String mail,
        String address,
        String phoneNumber,
        String website,
        Double rating
) {
}
