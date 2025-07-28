package com.tf.restocompras;

import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestocomprasApplication {

    private static final Logger log = LoggerFactory.getLogger(RestocomprasApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestocomprasApplication.class, args);


    }

    @Bean
    CommandLineRunner initializeDummyDataInTheDatabase(UserRepository userRepository,
                                                       CategoryRepository categoryRepository,
                                                       ProductRepository productRepository) {

        log.info("Creating dummy data in the database for testing purposes");

        return args -> {
            log.info("CommandLineRunner running in the Spring Boot Application");
            User admin = User.builder()
                    .id(1L)
                    .username("admin")
                    .email("fontanarrosatomas@gmail.com")
                    .password("admin")
                    .build();
            userRepository.save(admin);

            User user = User.builder()
                    .id(2L)
                    .username("joe")
                    .email("joe@gmail.com")
                    .password("password")
                    .build();
            userRepository.save(user);

            Category electronics = Category.builder()
                    .id(1L)
                    .name("Electronics")
                    .build();

            categoryRepository.save(electronics);

            Category pasta = Category.builder()
                    .id(2L)
                    .name("Pasta")
                    .build();

            categoryRepository.save(pasta);
            Category meat = Category.builder()
                    .id(3L)
                    .name("Meat")
                    .build();
            categoryRepository.save(meat);

            Category cleaning = Category.builder()
                    .id(4L)
                    .name("Cleaning")
                    .build();
            categoryRepository.save(cleaning);

            productRepository.save(Product.builder()
                    .id(1L)
                    .name("Laptop")
                    .description("Dell XPS 15")
                    .price(1500.0)
                    .image("https://www.dell.com/en-us/shop/dell-laptops/xps-15-laptop/spd/xps-15-9500-laptop")
                    .category(electronics)
                    .user(user)
                    .build());

            productRepository.save(Product.builder()
                    .id(2L)
                    .name("Spaghetti")
                    .description("Barilla Spaghetti 454g")
                    .price(2.0)
                    .image("https://hillshomemarket.com/wp-content/uploads/2013/03/Barilla-Spaghetti-Product-Detail-Page.jpg")
                    .category(pasta)
                    .user(user)
                    .build());

            log.info("Dummy data successfully created in the database for testing purposes");
        };
    }

}
