package com.tf.restocompras.repository;

import com.tf.restocompras.model.category.Category;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {

    public Optional<Category> findByName(String name);

}
