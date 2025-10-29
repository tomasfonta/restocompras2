package com.tf.restocompras.repository;

import com.tf.restocompras.model.bundle.Bundle;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.supplier.Supplier;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BundleRepository extends ListCrudRepository<Bundle, Long> {

    List<Bundle> findByItem(Item item);

    List<Bundle> findBySupplier(Supplier supplier);
}
