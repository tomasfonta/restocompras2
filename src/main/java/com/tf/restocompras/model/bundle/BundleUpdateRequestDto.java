package com.tf.restocompras.model.bundle;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BundleUpdateRequestDto {

    @NotNull
    private Long id;
    @NotNull
    private Double quantity;
    @NotNull
    private Double price;
    @NotNull
    private String unit;
    @NotNull
    private Long itemId;
    @NotNull
    private Long supplierId;
}
