package com.tf.restocompras.model.supplier;

import com.tf.restocompras.model.user.UserResponseDto;

import java.util.List;

public record SupplierResponseDto(
        Long id,
        String name,
        String mail,
        String address,
        String phoneNumber,
        String website,
        Double rating,
        List<UserResponseDto> users
) {
}
