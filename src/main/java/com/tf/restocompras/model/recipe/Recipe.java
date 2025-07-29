package com.tf.restocompras.model.recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "ingredients")
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @Positive
    private Duration cookingTime;
    @Positive
    private Integer monthlyServings;
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredients> ingredients;


}
