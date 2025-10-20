package com.tf.restocompras;

import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.SubCategoryLevel1Repository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestoComprasApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestoComprasApplication.class, args);
    }


    CommandLineRunner runner(UserRepository userRepository, SupplierService supplierService, CategoryRepository categoryRepository, ProductRepository productRepository, SubCategoryLevel1Repository subCategoryLevel1Repository) {
        if (categoryRepository.count() != 0) {
            return args -> {
            };
        }
        return args -> {

            //Create Categories
            var almacen = categoryRepository.save(
                    Category.builder()
                            .name("Almacen")
                            .build()
            );
            var bebidas = categoryRepository.save(
                    Category.builder()
                            .name("Bebidas")
                            .build()
            );
            var frescos = categoryRepository.save(
                    Category.builder()
                            .name("Frescos")
                            .build()
            );
            var congelados = categoryRepository.save(
                    Category.builder()
                            .name("Congelados")
                            .build()
            );
            var limpieza = categoryRepository.save(
                    Category.builder()
                            .name("Limpieza")
                            .build()
            );
            var equipamento = categoryRepository.save(
                    Category.builder()
                            .name("Equipamento")
                            .build()
            );
            var bazar = categoryRepository.save(
                    Category.builder()
                            .name("Bazar")
                            .build()
            );
            var descartables = categoryRepository.save(
                    Category.builder()
                            .name("Descartables")
                            .build()
            );

            // Create Subcategories Level 1


        };
    }
//        return args -> {
//            // Crear categorías por defecto
//            List<Category> defaultCategories = List.of(
//                    Category.builder().name("Almacén").build(),
//                    Category.builder().name("Pescados y Mariscos").build(),
//                    Category.builder().name("Frutas y Verduras Frescas").build(),
//                    Category.builder().name("Lácteos y Sustitutos").build(),
//                    Category.builder().name("Panificación y Repostería").build(),
//                    Category.builder().name("Granos, Pastas y Legumbres").build(),
//                    Category.builder().name("Abarrotes y Despensa").build(),
//                    Category.builder().name("Especias, Sazonadores y Salsas").build(),
//                    Category.builder().name("Bebidas Alcohólicas").build(),
//                    Category.builder().name("Bebidas No Alcohólicas").build(),
//                    Category.builder().name("Equipamiento de Cocina Mayor").build(),
//                    Category.builder().name("Utensilios, Menaje y Batería").build(),
//                    Category.builder().name("Vajilla, Cristalería y Cubertería").build(),
//                    Category.builder().name("Empaques y Desechables").build(),
//                    Category.builder().name("Limpieza, Higiene y Uniformes").build()
//            );
//
//            categoryRepository.saveAll(defaultCategories);
//
//            // Crear productos por defecto para cada categoría (subproductos clave)
//            var subproductsByCategory = Map.ofEntries(
//                    Map.entry("Almacén", List.of("Golosinas", "Panadería", "Snacks", "Cereales", "Endulzantes", "Carnes procesadas", "Jamones", "Salchichas"))
//            );
//
//            subproductsByCategory.forEach((categoryName, subproducts) ->
//                    categoryRepository.findByName(categoryName).ifPresent(cat -> {
//
//                                Category category = categoryRepository.getByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));
//
//                                subproducts.forEach(sp -> subCategoryLevel1Repository.save(SubCategoryLevel1.builder()
//                                        .name(sp)
//                                        .category(category)
//                                        .build()));
//                            }
//                    )
//            );
//        };
//    }

}
