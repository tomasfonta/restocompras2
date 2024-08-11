package com.tf.restocompras.repository;

import com.tf.restocompras.model.user.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;


public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
