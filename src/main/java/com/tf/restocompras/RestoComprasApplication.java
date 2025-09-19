package com.tf.restocompras;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.user.UserBusinessType;
import com.tf.restocompras.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class RestoComprasApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestoComprasApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepository) {

        if (userRepository.count() != 0) {
            return args -> {
            };
        }

        return args -> {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            User user = User.builder()
                    .password(encoder.encode("password"))
                    .username("user")
                    .role(ApplicationRoles.USER)
                    .email("user@gmail.com")
                    .userBusinessType(UserBusinessType.SUPPLIER)
                    .build();

            User admin = User.builder()
                    .password(encoder.encode("password"))
                    .username("admin")
                    .role(ApplicationRoles.ADMIN)
                    .email("admin@gmail.com")
                    .build();

            userRepository.saveAll(List.of(admin, user));
        };
    }

}
