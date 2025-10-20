package com.tf.restocompras.model.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sub_category_level1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryLevel1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "subCategoryLevel1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategoryLevel2> subCategoriesLevel2;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}