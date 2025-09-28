package com.tf.restocompras.model.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.unit.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;
    private String supplier;
    private Unit unit;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


}
