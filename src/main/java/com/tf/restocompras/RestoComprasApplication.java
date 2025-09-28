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
import java.util.Map;

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
                    Category.builder().name("Carnes, Aves y Embutidos").build(),
                    Category.builder().name("Pescados y Mariscos").build(),
                    Category.builder().name("Frutas y Verduras Frescas").build(),
                    Category.builder().name("Lácteos y Sustitutos").build(),
                    Category.builder().name("Panificación y Repostería").build(),
                    Category.builder().name("Granos, Pastas y Legumbres").build(),
                    Category.builder().name("Abarrotes y Despensa").build(),
                    Category.builder().name("Especias, Sazonadores y Salsas").build(),
                    Category.builder().name("Bebidas Alcohólicas").build(),
                    Category.builder().name("Bebidas No Alcohólicas").build(),
                    Category.builder().name("Equipamiento de Cocina Mayor").build(),
                    Category.builder().name("Utensilios, Menaje y Batería").build(),
                    Category.builder().name("Vajilla, Cristalería y Cubertería").build(),
                    Category.builder().name("Empaques y Desechables").build(),
                    Category.builder().name("Limpieza, Higiene y Uniformes").build()
            );

            categoryRepository.saveAll(defaultCategories);

            // Crear productos por defecto para cada categoría (subproductos clave)
            var subproductsByCategory = Map.ofEntries(
                    Map.entry("Carnes, Aves y Embutidos", List.of("Res", "Cerdo", "Cordero", "Pollo", "Pavo", "Carnes procesadas", "Jamones", "Salchichas")),
                    Map.entry("Pescados y Mariscos", List.of("Pescado fresco", "Pescado congelado", "Crustáceos", "Moluscos", "Ahumados")),
                    Map.entry("Frutas y Verduras Frescas", List.of("Hortalizas de hoja", "Tubérculos", "Frutas de temporada", "Hierbas frescas")),
                    Map.entry("Lácteos y Sustitutos", List.of("Leche", "Quesos", "Cremas", "Mantequilla", "Yogures", "Leches vegetales", "Quesos vegetales")),
                    Map.entry("Panificación y Repostería", List.of("Pan fresco", "Masa de hojaldre", "Masa de pizza", "Harinas", "Levaduras", "Ingredientes de pastelería", "Postres")),
                    Map.entry("Granos, Pastas y Legumbres", List.of("Arroz", "Frijoles", "Lentejas", "Pasta seca", "Cuscús", "Cereales para el desayuno")),
                    Map.entry("Abarrotes y Despensa", List.of("Aceites", "Vinagres", "Azúcares", "Miel", "Conservas", "Encurtidos", "Caldos", "Bases para sopa")),
                    Map.entry("Especias, Sazonadores y Salsas", List.of("Sal", "Pimienta", "Especias secas", "Aderezos", "Kétchup", "Mostaza", "Salsas picantes")),
                    Map.entry("Bebidas Alcohólicas", List.of("Vinos", "Cervezas", "Licores", "Destilados", "Cócteles premezclados")),
                    Map.entry("Bebidas No Alcohólicas", List.of("Aguas", "Refrescos", "Jugos", "Bebidas energéticas", "Jarabes", "Concentrados")),
                    Map.entry("Equipamiento de Cocina Mayor", List.of("Hornos industriales", "Estufas", "Freidoras de piso", "Neveras", "Congeladores")),
                    Map.entry("Utensilios, Menaje y Batería", List.of("Cuchillos", "Tablas de corte", "Ollas", "Sartenes", "Cubetas gastronómicas", "Electrodomésticos pequeños")),
                    Map.entry("Vajilla, Cristalería y Cubertería", List.of("Platos", "Cuencos", "Vasos", "Copas", "Tazas", "Cubiertos")),
                    Map.entry("Empaques y Desechables", List.of("Contenedores para take-away", "Servilletas de papel", "Bolsas", "Film plástico", "Aluminio")),
                    Map.entry("Limpieza, Higiene y Uniformes", List.of("Detergentes profesionales", "Desinfectantes", "Jabones", "Papel higiénico", "Guantes", "Uniformes", "Ropa de trabajo"))
            );

            subproductsByCategory.forEach((categoryName, subproducts) ->
                    categoryRepository.findByName(categoryName).ifPresent(cat ->
                            subproducts.forEach(sp -> productRepository.save(Product.builder()
                                    .name(sp)
                                    .category(cat)
                                    .build()))
                    )
            );

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
