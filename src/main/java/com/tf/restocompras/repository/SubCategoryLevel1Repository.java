package com.tf.restocompras.repository;

import com.tf.restocompras.model.category.SubCategoryLevel1;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SubCategoryLevel1Repository extends ListCrudRepository<SubCategoryLevel1, Long> {

    Optional<SubCategoryLevel1> findByName(String name);

    Optional<SubCategoryLevel1> getById(Long id);
}
