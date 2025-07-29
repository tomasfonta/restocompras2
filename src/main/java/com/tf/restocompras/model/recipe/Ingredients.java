package com.tf.restocompras.model.recipe;

import com.tf.restocompras.model.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String quantity;
    private String dimension;
    private BigDecimal cost;

    private String supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
