package com.tf.restocompras.repository;

import com.tf.restocompras.model.category.Category;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Optional<Category> getById(Long id);

    Optional<Category> getByName(String name);
}
