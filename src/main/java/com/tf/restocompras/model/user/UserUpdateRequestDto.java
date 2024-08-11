package com.tf.restocompras.model.user;

public record UserUpdateRequestDto(
        Long id,
        String name,
        String email,
        String password) {
}
