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
    @NotBlank
    private String brand;
    @NotBlank
    private String description;
    private Double price;
    private String image;
    private Long productId;
    @NotNull
    private Long supplierId;
    @NotNull
    private String unit;
    @NotNull
    private Double quantity;
}
