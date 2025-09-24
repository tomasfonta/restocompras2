package com.tf.restocompras.model.user;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    @Column(unique = true)
    @NotEmpty
    private String email;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @Column
    private ApplicationRoles role = ApplicationRoles.USER;
    @Column
    private UserBusinessType userBusinessType = UserBusinessType.SUPPLIER;

}