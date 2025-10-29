package com.tf.restocompras.model.bundle;

import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.unit.Unit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bundles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantity;
    private Double price;
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}