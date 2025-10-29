package com.tf.restocompras.model.supplier;

import com.tf.restocompras.model.bundle.Bundle;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mail;
    private String address;
    private String phoneNumber;
    private String website;
    private Double rating;
    @OneToOne(mappedBy = "supplier")
    private User user;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bundle> bundles;
}
