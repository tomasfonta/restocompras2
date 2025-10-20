package com.tf.restocompras.repository;

import com.tf.restocompras.model.category.SubCategoryLevel2;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SubCategoryLevel2Repository extends ListCrudRepository<SubCategoryLevel2, Long> {

    Optional<SubCategoryLevel2> findByName(String name);

    Optional<SubCategoryLevel2> getById(Long id);
}
