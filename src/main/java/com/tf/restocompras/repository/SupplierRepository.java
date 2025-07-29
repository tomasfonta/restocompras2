package com.tf.restocompras.repository;

import com.tf.restocompras.model.company.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}

