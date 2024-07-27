package com.tf.restocompras.repository;

import com.tf.restocompras.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}