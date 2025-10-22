package com.tf.restocompras.config.datainitializer;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.recipe.Recipe;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.user.UserBusinessType;
import com.tf.restocompras.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class UserDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserDataInitializer.class);

    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository,
                               SupplierRepository supplierRepository,
                               RestaurantRepository restaurantRepository,
                               ItemRepository itemRepository,
                               ProductRepository productRepository,
                               RecipeRepository recipeRepository,
                               IngredientRepository ingredientRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing supplier, restaurant and user data...");

        // Create supplier entity first
        Supplier supplier = null;
        if (supplierRepository.findById(1L).isEmpty()) {
            supplier = Supplier.builder()
                    .name("Test Supplier Company")
                    .mail("proveedor@test.com")
                    .address("123 Main Street, Business District")
                    .phoneNumber("+1-555-0123")
                    .website("https://www.testsupplier.com")
                    .rating(4.5)
                    .build();
            supplier = supplierRepository.save(supplier);
            log.info("Created supplier: {} (ID: {})", supplier.getName(), supplier.getId());
        } else {
            supplier = supplierRepository.findById(1L).get();
            log.info("Supplier already exists, skipping creation");
        }

        // Create restaurant entity first
        Restaurant restaurant = null;
        if (restaurantRepository.findById(1L).isEmpty()) {
            restaurant = Restaurant.builder()
                    .name("Test Restaurant")
                    .mail("restaurante@test.com")
                    .address("456 Food Avenue, Downtown")
                    .phoneNumber("+1-555-0456")
                    .website("https://www.testrestaurant.com")
                    .build();
            restaurant = restaurantRepository.save(restaurant);
            log.info("Created restaurant: {} (ID: {})", restaurant.getName(), restaurant.getId());
        } else {
            restaurant = restaurantRepository.findById(1L).get();
            log.info("Restaurant already exists, skipping creation");
        }

        // Create supplier user
        if (userRepository.findByEmail("proveedor@test.com").isEmpty()) {
            User supplierUser = User.builder()
                    .username("supplier")
                    .email("proveedor@test.com")
                    .password(passwordEncoder.encode("123123"))
                    .role(ApplicationRoles.USER)
                    .userBusinessType(UserBusinessType.SUPPLIER)
                    .supplier(supplier)
                    .build();

            User savedSupplierUser = userRepository.save(supplierUser);
            log.info("Created supplier user: {} with email: {} (ID: {})",
                    savedSupplierUser.getUsername(), savedSupplierUser.getEmail(), savedSupplierUser.getId());
        } else {
            log.info("Supplier user with email proveedor@test.com already exists, skipping creation");
        }

        // Create restaurant user
        if (userRepository.findByEmail("restaurante@test.com").isEmpty()) {
            User restaurantUser = User.builder()
                    .username("restaurant")
                    .email("restaurante@test.com")
                    .password(passwordEncoder.encode("123123"))
                    .role(ApplicationRoles.USER)
                    .userBusinessType(UserBusinessType.RESTAURANT)
                    .restaurant(restaurant)
                    .build();

            User savedRestaurantUser = userRepository.save(restaurantUser);
            log.info("Created restaurant user: {} with email: {} (ID: {})",
                    savedRestaurantUser.getUsername(), savedRestaurantUser.getEmail(), savedRestaurantUser.getId());
        } else {
            log.info("Restaurant user with email restaurante@test.com already exists, skipping creation");
        }

        // Create dairy supplier entity
        Supplier dairySupplier = null;
        if (supplierRepository.findById(2L).isEmpty()) {
            dairySupplier = Supplier.builder()
                    .name("Lácteos La Granja")
                    .mail("lacteos@lagranja.com")
                    .address("Ruta Nacional 5 Km 87, Zona Rural")
                    .phoneNumber("+1-555-0789")
                    .website("https://www.lacteoslagranja.com")
                    .rating(4.8)
                    .build();
            dairySupplier = supplierRepository.save(dairySupplier);
            log.info("Created dairy supplier: {} (ID: {})", dairySupplier.getName(), dairySupplier.getId());
        } else {
            dairySupplier = supplierRepository.findById(2L).get();
            log.info("Dairy supplier already exists, skipping creation");
        }

        // Create dairy supplier user
        if (userRepository.findByEmail("lacteos@test.com").isEmpty()) {
            User dairyUser = User.builder()
                    .username("lacteos")
                    .email("lacteos@test.com")
                    .password(passwordEncoder.encode("123123"))
                    .role(ApplicationRoles.USER)
                    .userBusinessType(UserBusinessType.SUPPLIER)
                    .supplier(dairySupplier)
                    .build();

            User savedDairyUser = userRepository.save(dairyUser);
            log.info("Created dairy supplier user: {} with email: {} (ID: {})",
                    savedDairyUser.getUsername(), savedDairyUser.getEmail(), savedDairyUser.getId());
        } else {
            log.info("Dairy supplier user with email lacteos@test.com already exists, skipping creation");
        }

        // Create initial item: Coca Cola 1L
        if (itemRepository.findById(1L).isEmpty()) {
            Product product = productRepository.findByName("Coca-Cola Clásica").orElseThrow();

            Item cocaCola = Item.builder()
                    .name("Coca Cola 1L")
                    .brand("Coca Cola")
                    .description("Coca Cola sabor original en botella de 1 litro")
                    .price(2.50)
                    .image("https://bodegabernardo.com/wp-content/uploads/2020/08/Coca-Cola-1L.jpg")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(product)
                    .build();

            Item savedItem = itemRepository.save(cocaCola);
            log.info("Created item: {} - {} (ID: {})", savedItem.getBrand(), savedItem.getName(), savedItem.getId());
        } else {
            log.info("Item Coca Cola 1L already exists, skipping creation");
        }

        // Create second item: Pepsi Común 1L
        if (itemRepository.findById(2L).isEmpty()) {
            Product pepsiProduct = productRepository.findByName("Pepsi Clásica").orElseThrow();

            Item pepsiComun = Item.builder()
                    .name("Pepsi Común 1L")
                    .brand("Pepsi")
                    .description("Pepsi sabor original en botella de 1 litro")
                    .price(2.30)
                    .image("https://5sentidos.es/wp-content/uploads/2021/02/Cola_Pepsi_botella_1_L_PET.jpg")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(pepsiProduct)
                    .build();

            Item savedPepsi = itemRepository.save(pepsiComun);
            log.info("Created item: {} - {} (ID: {})", savedPepsi.getBrand(), savedPepsi.getName(), savedPepsi.getId());
        } else {
            log.info("Item Pepsi Común 1L already exists, skipping creation");
        }

        // Create third item: Fanta Naranja 1L
        if (itemRepository.findById(3L).isEmpty()) {
            Product fantaProduct = productRepository.findByName("Fanta").orElse(null);

            Item fanta = Item.builder()
                    .name("Fanta Naranja 1L")
                    .brand("Fanta")
                    .description("Fanta sabor naranja en botella de 1 litro")
                    .price(2.20)
                    .image("https://www.comprar-bebidas.com/media/catalog/product/cache/5/image/767x1021/9df78eab33525d08d6e5fb8d27136e95/f/a/fanta_naranja_1l.jpg")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(fantaProduct)
                    .build();

            Item savedFanta = itemRepository.save(fanta);
            log.info("Created item: {} - {} (ID: {})", savedFanta.getBrand(), savedFanta.getName(), savedFanta.getId());
        } else {
            log.info("Item Fanta Naranja 1L already exists, skipping creation");
        }

        // Create ingredient items for the supplier (matching recipe ingredients but with lower prices)

        // Item 4: Lomo de res premium
        if (itemRepository.findById(4L).isEmpty()) {
            Product beefProduct = productRepository.findByName("Lomo").orElse(null);

            Item lomoRes = Item.builder()
                    .name("Lomo de res premium")
                    .brand("Premium Meats")
                    .description("Lomo de res de alta calidad para preparaciones gourmet")
                    .price(15.50)
                    .image("https://a.storyblok.com/f/160385/059728be58/carne.jpg/m/filters:quality(70)/")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(beefProduct)
                    .build();

            itemRepository.save(lomoRes);
            log.info("Created item: {} - {} (ID: {})", lomoRes.getBrand(), lomoRes.getName(), lomoRes.getId());
        }

        // Item 5: Masa de hojaldre
        if (itemRepository.findById(5L).isEmpty()) {
            Product puffPastryProduct = productRepository.findByName("Masa de Hojaldre").orElse(null);

            Item masaHojaldre = Item.builder()
                    .name("Masa de hojaldre")
                    .brand("La Cocinera")
                    .description("Masa de hojaldre congelada lista para usar")
                    .price(2.80)
                    .image("https://fripozo.com/wp-content/uploads/Masa-de-Hojaldre-1-1-1.png")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(puffPastryProduct)
                    .build();

            itemRepository.save(masaHojaldre);
            log.info("Created item: {} - {} (ID: {})", masaHojaldre.getBrand(), masaHojaldre.getName(), masaHojaldre.getId());
        }

        // Item 6: Champiñones
        if (itemRepository.findById(6L).isEmpty()) {
            Product mushroomsProduct = productRepository.findByName("Champiñones").orElse(null);

            Item champinones = Item.builder()
                    .name("Champiñones frescos")
                    .brand("Fresh Farm")
                    .description("Champiñones frescos de primera calidad")
                    .price(3.50)
                    .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdB-uaElImuU3tIdx0Mnh2jCwE_yFu-r9aag&s")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(mushroomsProduct)
                    .build();

            itemRepository.save(champinones);
            log.info("Created item: {} - {} (ID: {})", champinones.getBrand(), champinones.getName(), champinones.getId());
        }

        // Item 7: Paté de hígado de pato
        if (itemRepository.findById(7L).isEmpty()) {
            Product pateProduct = productRepository.findByName("Paté de Foie").orElse(null);

            Item pate = Item.builder()
                    .name("Paté de hígado de pato")
                    .brand("Gourmet Selection")
                    .description("Paté de hígado de pato de calidad premium")
                    .price(7.20)
                    .image("https://www.french-corner-shop.com/144-large_default/pate-de-higado-de-pato-reflets-de-france.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(pateProduct)
                    .build();

            itemRepository.save(pate);
            log.info("Created item: {} - {} (ID: {})", pate.getBrand(), pate.getName(), pate.getId());
        }

        // Item 8: Jamón Crudo
        if (itemRepository.findById(8L).isEmpty()) {
            Product prosciuttoProduct = productRepository.findByName("Jamón Crudo").orElse(null);

            Item jamonCrudo = Item.builder()
                    .name("Jamón Crudo en lonchas")
                    .brand("Ibérico Premium")
                    .description("Jamón crudo curado en lonchas finas")
                    .price(5.20)
                    .image("https://yourspanishcorner.com/1696-large_default/jamon-serrano-paleta-bodega-loncheado.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(prosciuttoProduct)
                    .build();

            itemRepository.save(jamonCrudo);
            log.info("Created item: {} - {} (ID: {})", jamonCrudo.getBrand(), jamonCrudo.getName(), jamonCrudo.getId());
        }

        // Item 9: Mostaza Dijon
        if (itemRepository.findById(9L).isEmpty()) {
            Product mustardProduct = productRepository.findByName("Mostaza Dijon").orElse(null);

            Item mostazaDijon = Item.builder()
                    .name("Mostaza Dijon")
                    .brand("Grey Poupon")
                    .description("Mostaza Dijon original de Francia")
                    .price(1.80)
                    .image("https://ametllerorigen.vtexassets.com/arquivos/ids/175809/59798---Mostaza-de-Dijon-Maille-215g--1-.jpg?v=638380663491130000")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(mustardProduct)
                    .build();

            itemRepository.save(mostazaDijon);
            log.info("Created item: {} - {} (ID: {})", mostazaDijon.getBrand(), mostazaDijon.getName(), mostazaDijon.getId());
        }

        log.info("Created 6 ingredient items for supplier");

        // Item 10: Harina para Pizza
        if (itemRepository.findById(10L).isEmpty()) {
            Product harinaProduct = productRepository.findByName("Harina para Pizza").orElse(null);

            Item harina = Item.builder()
                    .name("Harina para Pizza")
                    .brand("Molino Blanco")
                    .description("Harina especial para pizzas tipo napolitana")
                    .price(0.95) // Mejor precio que en receta (1.20)
                    .image("https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/202401/04/00118037902220____2__600x600.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(harinaProduct)
                    .build();

            itemRepository.save(harina);
            log.info("Created item: {} - {} (ID: {})", harina.getBrand(), harina.getName(), harina.getId());
        }

        // Item 11: Salsa Napolitana
        if (itemRepository.findById(11L).isEmpty()) {
            Product salsaProduct = productRepository.findByName("Salsa Napolitana").orElse(null);

            Item salsa = Item.builder()
                    .name("Salsa Napolitana")
                    .brand("La Campagnola")
                    .description("Salsa de tomate estilo napolitana para pastas y pizzas")
                    .price(1.10) // Peor precio que en receta (0.80)
                    .image("https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/202303/22/00118038400372____4__1200x1200.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(salsaProduct)
                    .build();

            itemRepository.save(salsa);
            log.info("Created item: {} - {} (ID: {})", salsa.getBrand(), salsa.getName(), salsa.getId());
        }

        // Item 12: Queso Mozzarella
        if (itemRepository.findById(12L).isEmpty()) {
            Product mozzarellaProduct = productRepository.findByName("Queso Mozzarella").orElse(null);

            Item mozzarella = Item.builder()
                    .name("Queso Mozzarella")
                    .brand("La Serenísima")
                    .description("Mozzarella para pizza en barra")
                    .price(3.20) // Mejor precio que en receta (3.50)
                    .image("https://www.dia.es/product_images/13096/13096_ISO_0_ES.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(mozzarellaProduct)
                    .build();

            itemRepository.save(mozzarella);
            log.info("Created item: {} - {} (ID: {})", mozzarella.getBrand(), mozzarella.getName(), mozzarella.getId());
        }

        // Item 13: Aceite de Oliva
        if (itemRepository.findById(13L).isEmpty()) {
            Product aceiteProduct = productRepository.findByName("Aceite de Oliva Extra Virgen").orElse(null);

            Item aceite = Item.builder()
                    .name("Aceite de Oliva Extra Virgen")
                    .brand("Cocinero")
                    .description("Aceite de oliva extra virgen primera prensada en frío")
                    .price(0.65) // Peor precio que en receta (0.50)
                    .image("https://ardiaprod.vtexassets.com/arquivos/ids/206765/Aceite-De-Oliva-Cocinero-Extra-Virgen-500-Ml-1-849259.jpg")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(aceiteProduct)
                    .build();

            itemRepository.save(aceite);
            log.info("Created item: {} - {} (ID: {})", aceite.getBrand(), aceite.getName(), aceite.getId());
        }

        // Item 14: Albahaca
        if (itemRepository.findById(14L).isEmpty()) {
            Product albahacaProduct = productRepository.findByName("Albahaca").orElse(null);

            Item albahaca = Item.builder()
                    .name("Albahaca Fresca")
                    .brand("Huerta Orgánica")
                    .description("Albahaca fresca cultivada orgánicamente")
                    .price(0.25) // Mejor precio que en receta (0.30)
                    .image("https://www.tutrebol.es/4629-large_default/hierbas-frescas-albahaca-50-gr.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(albahacaProduct)
                    .build();

            itemRepository.save(albahaca);
            log.info("Created item: {} - {} (ID: {})", albahaca.getBrand(), albahaca.getName(), albahaca.getId());
        }

        // Item 15: Carne Picada Especial
        if (itemRepository.findById(15L).isEmpty()) {
            Product carnePicadaProduct = productRepository.findByName("Carne Picada Especial").orElse(null);

            Item carnePicada = Item.builder()
                    .name("Carne Picada Especial")
                    .brand("Cabaña Las Lilas")
                    .description("Carne picada especial magra 90/10 para hamburguesas")
                    .price(4.20) // Peor precio que en receta (3.50)
                    .image("https://www.carniceriademadrid.es/wp-content/uploads/2021/04/carne-picada-2-600x400.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(carnePicadaProduct)
                    .build();

            itemRepository.save(carnePicada);
            log.info("Created item: {} - {} (ID: {})", carnePicada.getBrand(), carnePicada.getName(), carnePicada.getId());
        }

        // Item 16: Pan de Hamburguesa
        if (itemRepository.findById(16L).isEmpty()) {
            Product panHamburguesaProduct = productRepository.findByName("Pan de Hamburguesa").orElse(null);

            Item panHamburguesa = Item.builder()
                    .name("Pan de Hamburguesa")
                    .brand("Bimbo")
                    .description("Pan para hamburguesa con sésamo premium")
                    .price(0.50) // Mejor precio que en receta (0.60)
                    .image("https://www.infohoreca.com/media/uploads/noticias/pan-hamburguesa-brioche-infohoreca.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(panHamburguesaProduct)
                    .build();

            itemRepository.save(panHamburguesa);
            log.info("Created item: {} - {} (ID: {})", panHamburguesa.getBrand(), panHamburguesa.getName(), panHamburguesa.getId());
        }

        // Item 17: Queso Cheddar
        if (itemRepository.findById(17L).isEmpty()) {
            Product cheddarProduct = productRepository.findByName("Queso Cheddar").orElse(null);

            Item cheddar = Item.builder()
                    .name("Queso Cheddar")
                    .brand("Mendicrim")
                    .description("Queso Cheddar en fetas para hamburguesas")
                    .price(1.35) // Peor precio que en receta (1.20)
                    .image("https://arcordiezb2c.vteximg.com.br/arquivos/ids/181491/Queso-Cheddar-Fetas-Milkaut-145-Gr-1-13074.jpg?v=638404329704700000")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(cheddarProduct)
                    .build();

            itemRepository.save(cheddar);
            log.info("Created item: {} - {} (ID: {})", cheddar.getBrand(), cheddar.getName(), cheddar.getId());
        }

        // Item 18: Lechuga
        if (itemRepository.findById(18L).isEmpty()) {
            Product lechugaProduct = productRepository.findByName("Verduras").orElse(null);

            Item lechuga = Item.builder()
                    .name("Lechuga Mantecosa")
                    .brand("Granja Verde")
                    .description("Lechuga mantecosa fresca hidropónica")
                    .price(0.18) // Mejor precio que en receta (0.20)
                    .image("https://www.ramiroarnedo.com/wp-content/uploads/2024/09/Constantina-1024x769.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(lechugaProduct)
                    .build();

            itemRepository.save(lechuga);
            log.info("Created item: {} - {} (ID: {})", lechuga.getBrand(), lechuga.getName(), lechuga.getId());
        }

        // Item 19: Tomate
        if (itemRepository.findById(19L).isEmpty()) {
            Product tomateProduct = productRepository.findByName("Tomate").orElse(null);

            Item tomate = Item.builder()
                    .name("Tomate Perita")
                    .brand("Huerta Natural")
                    .description("Tomate perita fresco de estación")
                    .price(0.35) // Peor precio que en receta (0.30)
                    .image("https://frutasyverdurasalvaro.com/wp-content/uploads/2023/05/tomate-pera-cortado.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(tomateProduct)
                    .build();

            itemRepository.save(tomate);
            log.info("Created item: {} - {} (ID: {})", tomate.getBrand(), tomate.getName(), tomate.getId());
        }

        // Item 20: Ketchup
        if (itemRepository.findById(20L).isEmpty()) {
            Product ketchupProduct = productRepository.findByName("Ketchup Heinz").orElse(null);

            Item ketchup = Item.builder()
                    .name("Ketchup Heinz")
                    .brand("Heinz")
                    .description("Salsa ketchup clásica Heinz")
                    .price(0.12) // Mejor precio que en receta (0.15)
                    .image("https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/202202/15/00118016201214____4__600x600.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(ketchupProduct)
                    .build();

            itemRepository.save(ketchup);
            log.info("Created item: {} - {} (ID: {})", ketchup.getBrand(), ketchup.getName(), ketchup.getId());
        }

        // Item 21: Mayonesa
        if (itemRepository.findById(21L).isEmpty()) {
            Product mayonesaProduct = productRepository.findByName("Hellmanns Clásica").orElse(null);

            Item mayonesa = Item.builder()
                    .name("Mayonesa Hellmann's")
                    .brand("Hellmann's")
                    .description("Mayonesa clásica Hellmann's")
                    .price(0.18) // Peor precio que en receta (0.15)
                    .image("https://www.unileverfoodsolutions.com.ar/dam/global-ufs/mcos/sla/argentina/calcmenu/products/AR-products/packshots/hellmanns/hellmanns-may-clasica-sqz-24x320g/hellmanns-may-clasica-sqz-24x320g.jpg")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(supplier)
                    .product(mayonesaProduct)
                    .build();

            itemRepository.save(mayonesa);
            log.info("Created item: {} - {} (ID: {})", mayonesa.getBrand(), mayonesa.getName(), mayonesa.getId());
        }

        log.info("Created 12 additional items for supplier ingredients - Total supplier items: 21");

        // Create 10 dairy items for Lácteos La Granja supplier

        // Dairy Item 1: Leche Entera
        if (itemRepository.findById(22L).isEmpty()) {
            Product lecheProduct = productRepository.findByName("Leche Entera").orElse(null);

            Item lecheEntera = Item.builder()
                    .name("Leche Entera La Granja 1L")
                    .brand("La Granja")
                    .description("Leche entera pasteurizada fresca de vaca")
                    .price(1.20)
                    .image("https://d2r9epyceweg5n.cloudfront.net/stores/001/111/044/products/leche-la-serenisima-entera-1-lt1-d7dcae9a01e52ba56016037351003332-1024-1024.jpg")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(dairySupplier)
                    .product(lecheProduct)
                    .build();

            itemRepository.save(lecheEntera);
            log.info("Created dairy item: {} - {} (ID: {})", lecheEntera.getBrand(), lecheEntera.getName(), lecheEntera.getId());
        }

        // Dairy Item 2: Yogur Natural
        if (itemRepository.findById(23L).isEmpty()) {
            Product yogurProduct = productRepository.findByName("Yogur Natural").orElse(null);

            Item yogurNatural = Item.builder()
                    .name("Yogur Natural La Granja 1kg")
                    .brand("La Granja")
                    .description("Yogur natural cremoso sin azúcar agregada")
                    .price(3.80)
                    .image("https://www.danone.es/content/dam/danone-es/danone-es-aop/productos/natural-azucarado/yogur-natural-azucarado/yogur-natural-azucarado-pack4.png")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(dairySupplier)
                    .product(yogurProduct)
                    .build();

            itemRepository.save(yogurNatural);
            log.info("Created dairy item: {} - {} (ID: {})", yogurNatural.getBrand(), yogurNatural.getName(), yogurNatural.getId());
        }

        // Dairy Item 3: Queso Crema
        if (itemRepository.findById(24L).isEmpty()) {
            Product quesoCremaProduct = productRepository.findByName("Queso Crema Clásico").orElse(null);

            Item quesoCrema = Item.builder()
                    .name("Queso Crema La Granja 300g")
                    .brand("La Granja")
                    .description("Queso crema untable estilo Philadelphia")
                    .price(4.50)
                    .image("https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRkOOz3xxFbltZhbqoIMTmUppC9Z9ok-S-rDpXQGjdJiRLj3Yb9B_7-OFaGNjPffcwkN7g41a_MDXabShVQtyi8f3Z-riGXLV-hS25O9B2rncl7Z3b9E_cHAQ")
                    .unit(Unit.KG)
                    .quantity(0.3)
                    .supplier(dairySupplier)
                    .product(quesoCremaProduct)
                    .build();

            itemRepository.save(quesoCrema);
            log.info("Created dairy item: {} - {} (ID: {})", quesoCrema.getBrand(), quesoCrema.getName(), quesoCrema.getId());
        }

        // Dairy Item 4: Manteca
        if (itemRepository.findById(25L).isEmpty()) {
            Product mantecaProduct = productRepository.findByName("Manteca").orElse(null);

            Item manteca = Item.builder()
                    .name("Manteca La Granja 200g")
                    .brand("La Serenisima")
                    .description("Manteca de primera calidad ideal para repostería")
                    .price(2.90)
                    .image("https://restoshop.com.ar/wp-content/uploads/2024/06/manteca-la-serenisima-200gr.jpg")
                    .unit(Unit.KG)
                    .quantity(0.2)
                    .supplier(dairySupplier)
                    .product(mantecaProduct)
                    .build();

            itemRepository.save(manteca);
            log.info("Created dairy item: {} - {} (ID: {})", manteca.getBrand(), manteca.getName(), manteca.getId());
        }

        // Dairy Item 5: Crema de Leche
        if (itemRepository.findById(26L).isEmpty()) {
            Product cremaProduct = productRepository.findByName("Crema de Leche Clásica").orElse(null);

            Item cremaLeche = Item.builder()
                    .name("Crema de Leche La Granja 500ml")
                    .brand("La Granja")
                    .description("Crema de leche fresca para cocinar y batir")
                    .price(3.20)
                    .image("https://granjaelcuatro.com.mx/wp-content/uploads/2018/11/69-granja-el-4.png")
                    .unit(Unit.L)
                    .quantity(0.5)
                    .supplier(dairySupplier)
                    .product(cremaProduct)
                    .build();

            itemRepository.save(cremaLeche);
            log.info("Created dairy item: {} - {} (ID: {})", cremaLeche.getBrand(), cremaLeche.getName(), cremaLeche.getId());
        }

        // Dairy Item 6: Dulce de Leche
        if (itemRepository.findById(27L).isEmpty()) {
            Product dulceProduct = productRepository.findByName("Dulce de Leche Clásico").orElse(null);

            Item dulceLeche = Item.builder()
                    .name("Dulce de Leche La Granja 1kg")
                    .brand("La Granja")
                    .description("Dulce de leche artesanal cremoso y suave")
                    .price(5.80)
                    .image("https://media.area-gourmet.com/product/dulce-de-leche-havanna-250-g-800x800.jpg?width=1200")
                    .unit(Unit.KG)
                    .quantity(1.0)
                    .supplier(dairySupplier)
                    .product(dulceProduct)
                    .build();

            itemRepository.save(dulceLeche);
            log.info("Created dairy item: {} - {} (ID: {})", dulceLeche.getBrand(), dulceLeche.getName(), dulceLeche.getId());
        }

        // Dairy Item 7: Queso Parmesano Rallado
        if (itemRepository.findById(28L).isEmpty()) {
            Product paramesanoProduct = productRepository.findByName("Queso Rallado Parmesano").orElse(null);

            Item parmesano = Item.builder()
                    .name("Queso Parmesano Rallado La Granja 200g")
                    .brand("La Granja")
                    .description("Queso parmesano rallado madurado 12 meses")
                    .price(6.50)
                    .image("https://ardiaprod.vtexassets.com/arquivos/ids/197971/Queso-Rallado-Parmesano-La-Serenisima-40-Gr-1-14098.jpg")
                    .unit(Unit.KG)
                    .quantity(0.2)
                    .supplier(dairySupplier)
                    .product(paramesanoProduct)
                    .build();

            itemRepository.save(parmesano);
            log.info("Created dairy item: {} - {} (ID: {})", parmesano.getBrand(), parmesano.getName(), parmesano.getId());
        }

        // Dairy Item 8: Ricota
        if (itemRepository.findById(29L).isEmpty()) {
            Product ricotaProduct = productRepository.findByName("Ricotta").orElse(null);

            Item ricota = Item.builder()
                    .name("Ricota Fresca La Granja 500g")
                    .brand("La Granja")
                    .description("Ricota fresca ideal para pastas y tartas")
                    .price(3.90)
                    .image("https://media.istockphoto.com/id/1989575540/es/vector/milk-illustration.jpg?s=612x612&w=0&k=20&c=51u0AF20IZ-amvQsn79nE170-gJEv8CLttHq7AhvuKo=")
                    .unit(Unit.KG)
                    .quantity(0.5)
                    .supplier(dairySupplier)
                    .product(ricotaProduct)
                    .build();

            itemRepository.save(ricota);
            log.info("Created dairy item: {} - {} (ID: {})", ricota.getBrand(), ricota.getName(), ricota.getId());
        }

        // Dairy Item 9: Leche Chocolatada
        if (itemRepository.findById(30L).isEmpty()) {
            Product lecheChocolatadaProduct = productRepository.findByName("Leche Chocolatada").orElse(null);

            Item lecheChocolatada = Item.builder()
                    .name("Leche Chocolatada La Granja 1L")
                    .brand("La Granja")
                    .description("Leche con cacao y azúcar sabor chocolate")
                    .price(1.80)
                    .image("https://media.istockphoto.com/id/1989575540/es/vector/milk-illustration.jpg?s=612x612&w=0&k=20&c=51u0AF20IZ-amvQsn79nE170-gJEv8CLttHq7AhvuKo=")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(dairySupplier)
                    .product(lecheChocolatadaProduct)
                    .build();

            itemRepository.save(lecheChocolatada);
            log.info("Created dairy item: {} - {} (ID: {})", lecheChocolatada.getBrand(), lecheChocolatada.getName(), lecheChocolatada.getId());
        }

        // Dairy Item 10: Yogur Bebible con Frutas
        if (itemRepository.findById(31L).isEmpty()) {
            Product yogurBebibleProduct = productRepository.findByName("Yogur Bebible").orElse(null);

            Item yogurBebible = Item.builder()
                    .name("Yogur Bebible Frutilla La Granja 1L")
                    .brand("La Granja")
                    .description("Yogur bebible con trozos de frutilla natural")
                    .price(2.60)
                    .image("https://media.istockphoto.com/id/1989575540/es/vector/milk-illustration.jpg?s=612x612&w=0&k=20&c=51u0AF20IZ-amvQsn79nE170-gJEv8CLttHq7AhvuKo=")
                    .unit(Unit.L)
                    .quantity(1.0)
                    .supplier(dairySupplier)
                    .product(yogurBebibleProduct)
                    .build();

            itemRepository.save(yogurBebible);
            log.info("Created dairy item: {} - {} (ID: {})", yogurBebible.getBrand(), yogurBebible.getName(), yogurBebible.getId());
        }

        log.info("Created 10 dairy items for Lácteos La Granja supplier - Total items in system: 31");

        // Create recipe: Lomo Wellington for the restaurant
        if (recipeRepository.findById(1L).isEmpty()) {
            Recipe lomoWellington = Recipe.builder()
                    .name("Lomo Wellington")
                    .description("Filete de lomo de res envuelto en masa de hojaldre con paté y champiñones")
                    .price(new BigDecimal("45.00"))
                    .cookingTimeInMinutes(90)
                    .monthlyServings(50)
                    .isActive(true)
                    .instructions("1. Sellar el lomo en una sartén caliente. 2. Cubrir con paté y champiñones salteados. 3. Envolver en masa de hojaldre. 4. Hornear a 200°C por 40 minutos. 5. Dejar reposar 10 minutos antes de servir.")
                    .restaurant(restaurant)
                    .build();

            Recipe savedRecipe = recipeRepository.save(lomoWellington);
            log.info("Created recipe: {} (ID: {})", savedRecipe.getName(), savedRecipe.getId());

            // Create ingredients for the recipe
            Product beefProduct = productRepository.findById(1L).orElse(null);

            // Ingredient 1: Lomo de res
            Ingredient beefIngredient = Ingredient.builder()
                    .name("Lomo de res premium")
                    .quantity(new BigDecimal("0.500"))
                    .price(new BigDecimal("18.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(beefProduct)
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(beefIngredient);
            log.info("Created ingredient: {} for recipe {}", beefIngredient.getName(), savedRecipe.getName());

            // Ingredient 2: Masa de hojaldre
            Ingredient puffPastry = Ingredient.builder()
                    .name("Masa de hojaldre")
                    .quantity(new BigDecimal("0.400"))
                    .price(new BigDecimal("3.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Masa de Hojaldre").orElse(null))
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(puffPastry);
            log.info("Created ingredient: {} for recipe {}", puffPastry.getName(), savedRecipe.getName());

            // Ingredient 3: Champiñones
            Ingredient mushrooms = Ingredient.builder()
                    .name("Champiñones")
                    .quantity(new BigDecimal("0.300"))
                    .price(new BigDecimal("4.20"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Champiñones").orElse(null))
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(mushrooms);
            log.info("Created ingredient: {} for recipe {}", mushrooms.getName(), savedRecipe.getName());

            // Ingredient 4: Paté de hígado
            Ingredient pate = Ingredient.builder()
                    .name("Paté de hígado de pato")
                    .quantity(new BigDecimal("0.150"))
                    .price(new BigDecimal("8.90"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Paté de Foie").orElse(null))
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(pate);
            log.info("Created ingredient: {} for recipe {}", pate.getName(), savedRecipe.getName());

            // Ingredient 5: Jamón serrano
            Ingredient prosciutto = Ingredient.builder()
                    .name("Jamón Crudo")
                    .quantity(new BigDecimal("0.100"))
                    .price(new BigDecimal("6.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Jamón Crudo").orElse(null))
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(prosciutto);
            log.info("Created ingredient: {} for recipe {}", prosciutto.getName(), savedRecipe.getName());

            // Ingredient 6: Mostaza Dijon
            Ingredient mustard = Ingredient.builder()
                    .name("Mostaza Dijon")
                    .quantity(new BigDecimal("0.050"))
                    .price(new BigDecimal("2.30"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Mostaza Dijon").orElse(null))
                    .recipe(savedRecipe)
                    .build();
            ingredientRepository.save(mustard);
            log.info("Created ingredient: {} for recipe {}", mustard.getName(), savedRecipe.getName());

            log.info("Created 6 ingredients for recipe {}", savedRecipe.getName());
        } else {
            log.info("Recipe Lomo Wellington already exists, skipping creation");
        }

        // Create recipe: Pizza Margarita for the restaurant
        if (recipeRepository.findById(2L).isEmpty()) {
            Recipe pizzaMargarita = Recipe.builder()
                    .name("Pizza Margarita")
                    .description("Pizza clásica italiana con salsa de tomate, mozzarella fresca y albahaca")
                    .price(new BigDecimal("25.00"))
                    .cookingTimeInMinutes(20)
                    .monthlyServings(120)
                    .isActive(true)
                    .instructions("1. Estirar la masa de pizza. 2. Cubrir con salsa de tomate. 3. Agregar mozzarella en rodajas. 4. Hornear a 250°C por 12-15 minutos. 5. Decorar con hojas de albahaca fresca y aceite de oliva.")
                    .restaurant(restaurant)
                    .build();

            Recipe savedPizza = recipeRepository.save(pizzaMargarita);
            log.info("Created recipe: {} (ID: {})", savedPizza.getName(), savedPizza.getId());

            // Create ingredients for Pizza Margarita

            // Ingredient 1: Harina para pizza
            Ingredient harina = Ingredient.builder()
                    .name("Harina para Pizza")
                    .quantity(new BigDecimal("0.300"))
                    .price(new BigDecimal("1.20"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Harina para Pizza").orElse(null))
                    .recipe(savedPizza)
                    .build();
            ingredientRepository.save(harina);
            log.info("Created ingredient: {} for recipe {}", harina.getName(), savedPizza.getName());

            // Ingredient 2: Salsa de tomate
            Ingredient salsa = Ingredient.builder()
                    .name("Salsa de Tomate")
                    .quantity(new BigDecimal("0.150"))
                    .price(new BigDecimal("0.80"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Salsa Napolitana").orElse(null))
                    .recipe(savedPizza)
                    .build();
            ingredientRepository.save(salsa);
            log.info("Created ingredient: {} for recipe {}", salsa.getName(), savedPizza.getName());

            // Ingredient 3: Mozzarella
            Ingredient mozzarella = Ingredient.builder()
                    .name("Queso Mozzarella")
                    .quantity(new BigDecimal("0.200"))
                    .price(new BigDecimal("3.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Queso Mozzarella").orElse(null))
                    .recipe(savedPizza)
                    .build();
            ingredientRepository.save(mozzarella);
            log.info("Created ingredient: {} for recipe {}", mozzarella.getName(), savedPizza.getName());

            // Ingredient 4: Aceite de oliva
            Ingredient aceite = Ingredient.builder()
                    .name("Aceite de Oliva")
                    .quantity(new BigDecimal("0.020"))
                    .price(new BigDecimal("0.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.L)
                    .product(productRepository.findByName("Aceite de Oliva Extra Virgen").orElse(null))
                    .recipe(savedPizza)
                    .build();
            ingredientRepository.save(aceite);
            log.info("Created ingredient: {} for recipe {}", aceite.getName(), savedPizza.getName());

            // Ingredient 5: Albahaca
            Ingredient albahaca = Ingredient.builder()
                    .name("Albahaca Fresca")
                    .quantity(new BigDecimal("0.010"))
                    .price(new BigDecimal("0.30"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Albahaca").orElse(null))
                    .recipe(savedPizza)
                    .build();
            ingredientRepository.save(albahaca);
            log.info("Created ingredient: {} for recipe {}", albahaca.getName(), savedPizza.getName());

            log.info("Created 5 ingredients for recipe {}", savedPizza.getName());
        } else {
            log.info("Recipe Pizza Margarita already exists, skipping creation");
        }

        // Create recipe: Hamburguesa con Queso for the restaurant
        if (recipeRepository.findById(3L).isEmpty()) {
            Recipe hamburguesaQueso = Recipe.builder()
                    .name("Hamburguesa con Queso")
                    .description("Hamburguesa clásica con carne de res, queso cheddar, lechuga, tomate y salsa especial")
                    .price(new BigDecimal("18.00"))
                    .cookingTimeInMinutes(15)
                    .monthlyServings(200)
                    .isActive(true)
                    .instructions("1. Cocinar la hamburguesa a la plancha 4 minutos por lado. 2. Agregar queso cheddar al final. 3. Tostar el pan. 4. Armar con lechuga, tomate, hamburguesa con queso y salsas. 5. Servir con papas fritas.")
                    .restaurant(restaurant)
                    .build();

            Recipe savedHamburguesa = recipeRepository.save(hamburguesaQueso);
            log.info("Created recipe: {} (ID: {})", savedHamburguesa.getName(), savedHamburguesa.getId());

            // Create ingredients for Hamburguesa con Queso

            // Ingredient 1: Carne picada para hamburguesas
            Ingredient carnePicada = Ingredient.builder()
                    .name("Carne Picada Especial")
                    .quantity(new BigDecimal("0.200"))
                    .price(new BigDecimal("3.50"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Carne Picada Especial").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(carnePicada);
            log.info("Created ingredient: {} for recipe {}", carnePicada.getName(), savedHamburguesa.getName());

            // Ingredient 2: Pan de hamburguesa
            Ingredient panHamburguesa = Ingredient.builder()
                    .name("Pan de Hamburguesa")
                    .quantity(new BigDecimal("0.100"))
                    .price(new BigDecimal("0.60"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Pan de Hamburguesa").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(panHamburguesa);
            log.info("Created ingredient: {} for recipe {}", panHamburguesa.getName(), savedHamburguesa.getName());

            // Ingredient 3: Queso Cheddar
            Ingredient quesoCheddar = Ingredient.builder()
                    .name("Queso Cheddar")
                    .quantity(new BigDecimal("0.050"))
                    .price(new BigDecimal("1.20"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Queso Cheddar").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(quesoCheddar);
            log.info("Created ingredient: {} for recipe {}", quesoCheddar.getName(), savedHamburguesa.getName());

            // Ingredient 4: Lechuga
            Ingredient lechuga = Ingredient.builder()
                    .name("Lechuga Fresca")
                    .quantity(new BigDecimal("0.030"))
                    .price(new BigDecimal("0.20"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Lechuga").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(lechuga);
            log.info("Created ingredient: {} for recipe {}", lechuga.getName(), savedHamburguesa.getName());

            // Ingredient 5: Tomate
            Ingredient tomate = Ingredient.builder()
                    .name("Tomate")
                    .quantity(new BigDecimal("0.050"))
                    .price(new BigDecimal("0.30"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Verduras").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(tomate);
            log.info("Created ingredient: {} for recipe {}", tomate.getName(), savedHamburguesa.getName());

            // Ingredient 6: Ketchup
            Ingredient ketchup = Ingredient.builder()
                    .name("Ketchup")
                    .quantity(new BigDecimal("0.020"))
                    .price(new BigDecimal("0.15"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Ketchup Heinz").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(ketchup);
            log.info("Created ingredient: {} for recipe {}", ketchup.getName(), savedHamburguesa.getName());

            // Ingredient 7: Mayonesa
            Ingredient mayonesa = Ingredient.builder()
                    .name("Mayonesa")
                    .quantity(new BigDecimal("0.020"))
                    .price(new BigDecimal("0.15"))
                    .supplier("Test Supplier Company")
                    .unit(Unit.KG)
                    .product(productRepository.findByName("Hellmanns Clásica").orElse(null))
                    .recipe(savedHamburguesa)
                    .build();
            ingredientRepository.save(mayonesa);
            log.info("Created ingredient: {} for recipe {}", mayonesa.getName(), savedHamburguesa.getName());

            log.info("Created 7 ingredients for recipe {}", savedHamburguesa.getName());
        } else {
            log.info("Recipe Hamburguesa con Queso already exists, skipping creation");
        }
    }
}

