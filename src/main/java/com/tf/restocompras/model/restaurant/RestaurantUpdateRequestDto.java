package com.tf.restocompras.model.restaurant;

public record RestaurantUpdateRequestDto(
        Long id,
        String name,
        String mail,
        String address,
        String phoneNumber,
        String website
) {
}
