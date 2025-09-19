package com.tf.restocompras.model.user;

import lombok.Builder;

@Builder
public record UserCreateRequestDto(
        String name,
        String email,
        String password,
        UserBusinessType userBusinessType
) {
}
