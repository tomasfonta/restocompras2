package com.tf.restocompras.model.company;


import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mail;
    private String address;
    private String phoneNumber;
    private String website;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipe> recipes;
}
