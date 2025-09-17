package com.tf.restocompras.model.user;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;


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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @NotEmpty
    private ApplicationRoles role = ApplicationRoles.USER;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(ApplicationRoles role) {
        this.role = role;
    }

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = ApplicationRoles.USER;
        }
    }
}