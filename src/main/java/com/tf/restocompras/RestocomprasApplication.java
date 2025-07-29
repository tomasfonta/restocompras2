package com.tf.restocompras;

import com.tf.restocompras.model.company.Restaurant;
import com.tf.restocompras.model.company.Supplier;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.repository.*;
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
                                                       ItemRepository itemRepository,
                                                       RestaurantRepository restaurantRepository,
                                                       SupplierRepository supplierRepository,
                                                       ProductRepository productRepository) {

        log.info("Creating dummy data in the database for testing purposes");

        return args -> {
            log.info("CommandLineRunner running in the Spring Boot Application");


            User userSupplier = new User();
            User userRestaurant = new User();
            Supplier supplier = new Supplier();
            Restaurant restaurant = new Restaurant();

            userSupplier = User.builder()
                    .username("suppler")
                    .password("password")
                    .email("supplier@email.com")
                    .build();

            userRestaurant = User.builder()
                    .username("restaurant")
                    .password("password")
                    .email("restaurant@email.com")
                    .build();

            restaurant = Restaurant.builder()
                    .name("Pizza House")
                    .mail("restaurant@email.com")
                    .address("Pallegrini 1234, Rosario, Santa Fe, Argentina")
                    .phoneNumber("555-1234")
                    .website("www.pizzapalace.com")
                    .build();


            supplier = Supplier.builder()
                    .name("Proveedor")
                    .mail("supplier@email.com")
                    .address("Eva Peron 2000, Rosario, Santa Fe, Argentina")
                    .phoneNumber("555-5678")
                    .website("www.freshfoods.com")
                    .rating(4.8)
                    .deliveryTime(24)
                    .build();

            Category pasta = Category.builder()
                    .name("Pasta")
                    .build();
            categoryRepository.save(pasta);

            Category verduras = Category.builder()
                    .name("Verduras")
                    .build();
            categoryRepository.save(verduras);

            Category lacteos = Category.builder()
                    .name("Lacteos")
                    .build();
            categoryRepository.save(lacteos);

            Product productPasta = Product.builder()
                    .name("Pasta")
                    .category(pasta)
                    .build();

            productRepository.save(productPasta);


            userRepository.save(userRestaurant);
            userRepository.save(userSupplier);
            supplierRepository.save(supplier);
            restaurantRepository.save(restaurant);

            userSupplier.setSupplier(supplier);
            userRestaurant.setRestaurant(restaurant);

             userRepository.save(userSupplier);
             userRepository.save(userRestaurant);

            itemRepository.save(Item.builder()
                    .name("Leche Descremada")
                    .description("Leche Descremada 1L La Serenisima")
                    .price(2400.0)
                    .image("https://jumboargentina.vtexassets.com/arquivos/ids/853882/Leche-Descremada-La-Seren-sima-1sachet-1lt-1-849813.jpg?v=638733299806900000")
                    .product(productPasta)
                    .user(userSupplier)
                    .build());

            itemRepository.save(Item.builder()
                    .name("Spaghetti")
                    .description("Barilla Spaghetti 454g")
                    .price(2400.0)
                    .image("https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/202103/15/00118003501170____12__600x600.jpg")
                    .product(productPasta)
                    .user(userSupplier)
                    .build());

            log.info("Dummy data successfully created in the database for testing purposes");
        };
    }

}
