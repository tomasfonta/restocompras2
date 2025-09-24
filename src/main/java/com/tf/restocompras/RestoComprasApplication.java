package com.tf.restocompras;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.user.UserBusinessType;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.SupplierService;
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
    CommandLineRunner runner(UserRepository userRepository, SupplierService supplierService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        if (userRepository.count() != 0) {
            return args -> {
            };
        }
        return args -> {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            // Crear categorías por defecto
            List<Category> defaultCategories = List.of(
                    Category.builder().name("Bebidas").build(),
                    Category.builder().name("Carnes").build(),
                    Category.builder().name("Verduras").build(),
                    Category.builder().name("Lácteos").build(),
                    Category.builder().name("Panificados").build()
            );

            categoryRepository.saveAll(defaultCategories);

            // Crear productos por defecto para cada categoría
            for (Category category : defaultCategories) {
                String productName = switch (category.getName()) {
                    case "Bebidas" -> "Agua Tónica";
                    case "Carnes" -> "Pollo";
                    case "Verduras" -> "Tomate";
                    case "Lácteos" -> "Leche descremada";
                    case "Panificados" -> "Pan francés";
                    default -> "Producto de " + category.getName();
                };
                Product product = Product.builder()
                        .name(productName)
                        .category(categoryRepository.findByName(category.getName()).get())
                        .build();
                productRepository.save(product);
            }
            
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
