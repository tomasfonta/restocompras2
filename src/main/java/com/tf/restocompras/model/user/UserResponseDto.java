package com.tf.restocompras.model.user;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.supplier.SupplierResponseDto;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        ApplicationRoles applicationRoles,
        UserBusinessType userBusinessType,
        SupplierResponseDto supplier
) {
}
