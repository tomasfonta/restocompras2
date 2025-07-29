package com.tf.restocompras.repository;

import com.tf.restocompras.model.company.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}

