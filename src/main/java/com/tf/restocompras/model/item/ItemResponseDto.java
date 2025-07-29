package com.tf.restocompras.model.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemResponseDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long productId;
    @NotNull
    private Long userId;
}
