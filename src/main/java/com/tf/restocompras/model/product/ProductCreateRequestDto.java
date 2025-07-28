package com.tf.restocompras.model.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCreateRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    @NotBlank
    private String image;
    @NotBlank
    private String categoryName;
    @NotNull
    private Long userId;
}
