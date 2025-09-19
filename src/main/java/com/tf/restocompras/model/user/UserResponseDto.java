package com.tf.restocompras.model.user;

import com.tf.restocompras.config.security.ApplicationRoles;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        ApplicationRoles applicationRoles,
        UserBusinessType userBusinessType
) {
}
