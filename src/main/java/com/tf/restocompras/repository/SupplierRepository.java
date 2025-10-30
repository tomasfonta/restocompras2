package com.tf.restocompras.repository;

import com.tf.restocompras.model.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findById(Long id);

    Optional<Supplier> findByUserId(Long userId);

    Optional<Supplier> findByMail(String mail);

    @Query("SELECT s FROM Supplier s WHERE LOWER(s.mail) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Supplier> findByEmailContaining(@Param("email") String email);

}
