package com.multitenant.config;

import com.multitenant.core.TenantAwareDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import javax.sql.DataSource;
import java.util.*;

@Configuration
@EnableConfigurationProperties(TenantConfigProperties.class)
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(TenantConfigProperties config) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (TenantConfigProperties.Tenant tenant : config.getTenants()) {
            // Create DataSource for each tenant (e.g., using HikariCP)
            DataSource ds = DataSourceBuilder.create()
                    .url(tenant.getConnectionString())
                    .build();
            targetDataSources.put(tenant.getIdentifier(), ds);
        }
        TenantAwareDataSource dataSource = new TenantAwareDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(
                DataSourceBuilder.create().url(config.getDefaults().getConnectionString()).build()
        );
        return dataSource;
    }
}