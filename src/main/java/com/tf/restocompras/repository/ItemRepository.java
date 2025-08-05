package com.tf.restocompras.repository;

import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.item.Item;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ItemRepository extends ListCrudRepository<Item, Long> {

    List<Item> findBySupplier(Supplier supplier);

}