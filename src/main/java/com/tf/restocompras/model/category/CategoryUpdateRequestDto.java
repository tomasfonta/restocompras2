package com.tf.restocompras.model.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryUpdateRequestDto(
        @NotNull
        Long id,
        @NotBlank
        String name

) {
}