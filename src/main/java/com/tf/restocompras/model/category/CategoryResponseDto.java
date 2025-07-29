package com.tf.restocompras.model.category;

import com.tf.restocompras.model.item.ItemResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        List<ItemResponseDto> products) {

}
