package com.tf.restocompras.model.bundle;

import com.tf.restocompras.model.item.ItemResponseDto;
import com.tf.restocompras.model.supplier.SupplierResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BundleResponseDto {

    @NotNull
    private Long id;
    @NotNull
    private Double quantity;
    @NotNull
    private Double price;
    @NotNull
    private String unit;
    @NotNull
    private ItemResponseDto item;
    @NotNull
    private SupplierResponseDto supplier;
}
