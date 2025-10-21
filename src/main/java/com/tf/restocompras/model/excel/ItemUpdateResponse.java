package com.tf.restocompras.model.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemUpdateResponse {
    private List<Long> updatedItemIds;
    private List<Long> createdItemIds;
    private int totalUpdated;
    private int totalCreated;
    private int totalProcessed;
}

