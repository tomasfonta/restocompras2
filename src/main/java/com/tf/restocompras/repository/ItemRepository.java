package com.tf.restocompras.repository;

import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.item.Item;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ItemRepository extends ListCrudRepository<Item, Long> {

}