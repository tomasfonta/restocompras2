package com.tf.restocompras.model.product;


import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.recipe.Ingredients;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredients> ingredients;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
