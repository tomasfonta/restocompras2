package com.tf.restocompras.config.datainitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseInitConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitConfig.class);
    private final DataSource dataSource;

    public DatabaseInitConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public CommandLineRunner runDbInitScript(
            @Value("${app.db.init.enabled:true}") boolean enabled,
            @Value("${app.db.init.script:db/init-categories.sql}") String scriptPath
    ) {
        return args -> {
            if (!enabled) {
                log.info("Database init script is disabled (app.db.init.enabled=false)");
                return;
            }
            Resource resource = new ClassPathResource(scriptPath);
            if (!resource.exists()) {
                log.warn("Database init script not found on classpath: {}", scriptPath);
                return;
            }
            log.info("Executing database init script: {}", scriptPath);
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
            populator.setContinueOnError(false);
            populator.setIgnoreFailedDrops(true);
            populator.execute(dataSource);
            log.info("Database init script executed successfully");
        };
    }
}

