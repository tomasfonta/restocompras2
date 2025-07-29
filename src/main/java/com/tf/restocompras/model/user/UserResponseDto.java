package com.tf.restocompras.model.user;

import java.util.UUID;

public record UserResponseDto(
        Long id,
        String name,
        String email) {
}
