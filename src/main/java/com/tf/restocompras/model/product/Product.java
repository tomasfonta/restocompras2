package com.tf.restocompras.model.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tf.restocompras.model.category.SubCategoryLevel2;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product")
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "sub_category_level2_id", nullable = false)
    private SubCategoryLevel2 subCategoryLevel2;
}
