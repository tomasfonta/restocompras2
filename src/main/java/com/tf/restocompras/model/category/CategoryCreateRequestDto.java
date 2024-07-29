package com.tf.restocompras.model.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryCreateRequestDto(
        @NotBlank
        String name
) {
}
