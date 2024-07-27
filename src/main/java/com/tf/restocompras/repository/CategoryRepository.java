package com.tf.restocompras.repository;

import com.tf.restocompras.model.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {
}
