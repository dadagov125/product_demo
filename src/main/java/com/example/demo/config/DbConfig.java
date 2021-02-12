package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class DbConfig {

    private final String scriptDir = "sql";

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

        ClassPathResource[] a = new ClassPathResource[0];


        Arrays.stream(getScriptNames())
                .forEach((s -> {
                    resourceDatabasePopulator.addScript(new ClassPathResource(scriptDir + "/" + s));
                }));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    private String[] getScriptNames() {
        return new String[]{
                "create_product.sql"
        };
    }
}
