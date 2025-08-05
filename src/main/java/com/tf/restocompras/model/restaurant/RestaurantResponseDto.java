package com.tf.restocompras.model.restaurant;

import com.tf.restocompras.model.user.UserResponseDto;

import java.util.List;

public record RestaurantResponseDto(
        Long id,
        String name,
        String mail,
        String address,
        String phoneNumber,
        String website,
        List<UserResponseDto> users
) {
}
