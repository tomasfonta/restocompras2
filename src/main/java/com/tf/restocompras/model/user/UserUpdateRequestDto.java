package com.tf.restocompras.model.user;

import java.util.UUID;

public record UserUpdateRequestDto(
        Long id,
        String name,
        String email,
        String password) {
}
