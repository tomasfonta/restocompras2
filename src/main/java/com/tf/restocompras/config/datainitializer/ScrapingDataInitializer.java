package com.tf.restocompras.config.datainitializer;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.user.UserBusinessType;
import com.tf.restocompras.repository.RestaurantRepository;
import com.tf.restocompras.repository.SupplierRepository;
import com.tf.restocompras.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Initializes users and suppliers/restaurants from a scraped CSV file located at classpath:db/scraped-users-suppliers.csv
 * The CSV expected header (comma separated):
 * email,username,password,role,businessType,supplierName,supplierMail,supplierAddress,supplierPhone,website
 * For SUPPLIER businessType: supplierName, supplierMail, supplierAddress, supplierPhone, website are used for supplier creation
 * For RESTAURANT businessType: supplierName is treated as restaurantName, supplierMail as restaurantMail, etc.
 * Lines with missing name will create only the user.
 * This initializer is enabled by property: app.scraping.init.enabled (default: false)
 */
@Component
@Order(1)
public class ScrapingDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ScrapingDataInitializer.class);

    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    public ScrapingDataInitializer(UserRepository userRepository,
                                   SupplierRepository supplierRepository,
                                   RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        Resource resource = new ClassPathResource("db/scraped-users-suppliers.csv");
        if (!resource.exists()) {
            log.warn("Scraped CSV not found on classpath: db/scraped-users-suppliers.csv - skipping scraping initializer");
            return;
        }

        log.info("Executing scraping initializer from: db/scraped-users-suppliers.csv");

        try (InputStream is = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String header = reader.readLine();
            if (header == null) {
                log.warn("Scraped CSV is empty");
                return;
            }
            // simple CSV parsing: split by comma (no support for quoted commas)
            String line;
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] cols = line.split(",");
                // expected columns: email,username,password,role,businessType,supplierName,supplierMail,supplierAddress,supplierPhone,website
                String email = cols.length > 0 ? cols[0].trim() : "";
                String username = cols.length > 1 ? cols[1].trim() : null;
                String password = cols.length > 2 ? cols[2].trim() : "123123";
                String role = cols.length > 3 ? cols[3].trim() : "USER";
                String businessType = cols.length > 4 ? cols[4].trim() : "SUPPLIER";
                String supplierName = cols.length > 5 ? cols[5].trim() : null;
                String supplierMail = cols.length > 6 ? cols[6].trim() : null;
                String supplierAddress = cols.length > 7 ? cols[7].trim() : null;
                String supplierPhone = cols.length > 8 ? cols[8].trim() : null;
                String website = cols.length > 9 ? cols[9].trim() : null;

                if (email.isEmpty()) {
                    log.warn("Line {}: email is empty, skipping", lineNo);
                    continue;
                }

                Optional<User> existing = userRepository.findByEmail(email);
                if (existing.isPresent()) {
                    log.info("Line {}: user with email {} already exists, skipping", lineNo, email);
                    continue;
                }

                User.UserBuilder userBuilder = User.builder()
                        .email(email)
                        .username(username == null || username.isEmpty() ? email.split("@")[0] : username)
                        .password(passwordEncoder.encode(password))
                        .role("ADMIN".equalsIgnoreCase(role) ? ApplicationRoles.ADMIN : ApplicationRoles.USER)
                        .userBusinessType("RESTAURANT".equalsIgnoreCase(businessType) ? UserBusinessType.RESTAURANT : UserBusinessType.SUPPLIER);

                if ("RESTAURANT".equalsIgnoreCase(businessType)) {

                    if (supplierName != null && !supplierName.isEmpty()) {

                        Restaurant restaurant = Restaurant.builder()
                                .name(supplierName)
                                .mail(supplierMail)
                                .address(supplierAddress)
                                .phoneNumber(supplierPhone)
                                .website(website)
                                .build();

                        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
                        log.info("Line {}: created restaurant {} (id={})", lineNo, savedRestaurant.getName(), savedRestaurant.getId());
                        userBuilder.restaurant(savedRestaurant);
                    } else {
                        log.info("Line {}: restaurant user {} created without restaurant entity (no name provided)", lineNo, email);
                    }
                } else {
                    if (supplierName != null && !supplierName.isEmpty()) {
                        Supplier.SupplierBuilder sb = Supplier.builder()
                                .name(supplierName)
                                .mail(supplierMail)
                                .address(supplierAddress)
                                .phoneNumber(supplierPhone)
                                .website(website)
                                .rating(0.0);

                        Supplier supplier = supplierRepository.save(sb.build());
                        log.info("Line {}: created supplier {} (id={})", lineNo, supplier.getName(), supplier.getId());
                        userBuilder.supplier(supplier);
                    }
                }

                User user = userRepository.save(userBuilder.build());
                log.info("Line {}: created user {} with email {} (id={})", lineNo, user.getUsername(), user.getEmail(), user.getId());
            }
        } catch (Exception e) {
            log.error("Error while executing scraping initializer", e);
            throw e;
        }

        log.info("Scraping initializer finished");
    }
}
