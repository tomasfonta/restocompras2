package com.tf.restocompras.model.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "product")
    private List<Item> items;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
