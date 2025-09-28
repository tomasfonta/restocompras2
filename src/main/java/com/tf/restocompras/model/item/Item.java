package com.tf.restocompras.model.item;


import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotBlank
    private String description;
    private Double price;
    private String image;
    @NotNull
    private Unit unit;
    @NotNull
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}