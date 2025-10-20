package com.tf.restocompras.model.category;

import com.tf.restocompras.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sub_category_level2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryLevel2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "subCategoryLevel2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "sub_category_level1_id", nullable = false)
    private SubCategoryLevel1 subCategoryLevel1;
}