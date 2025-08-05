package com.tf.restocompras.model.restaurant;

public record RestaurantCreateRequestDto(
        String name,
        String mail,
        String address,
        String phoneNumber,
        String website
) {
}
