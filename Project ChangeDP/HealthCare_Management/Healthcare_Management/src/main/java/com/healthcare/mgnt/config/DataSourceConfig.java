package com.healthcare.mgnt.config;

import com.multitenantlib.context.TenantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for multi-tenant data source and JPA setup.
 * Sets up routing data source, entity manager, and transaction manager for tenant-aware operations.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.healthcare.mgnt.repository",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Autowired
    private Environment env;

    /**
     * Creates the main DataSource bean with tenant routing logic.
     * @return DataSource instance
     */
    @Bean
    public DataSource dataSource() {
        DataSource defaultDataSource = buildDefaultDataSource();

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return TenantContext.getCurrentTenant();
            }
        };

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("sims", createDataSourceForSchema("sims"));
        dataSources.put("miot", createDataSourceForSchema("miot"));
        dataSources.put("apollo", createDataSourceForSchema("apollo"));

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(defaultDataSource);

        return routingDataSource;
    }

    /**
     * Builds the default DataSource for fallback or single-tenant use.
     * @return DataSource instance
     */
    private DataSource buildDefaultDataSource() {
        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .driverClassName(dbDriverClassName)
                .build();
    }

    private DataSource createDataSourceForSchema(String schema) {
        // For now, use defaultDataSource with schema set
        // You can customize this to use schema-specific DataSource if needed
        return buildDefaultDataSource();
    }

    /**
     * Configures the EntityManagerFactory for JPA operations.
     * @return LocalContainerEntityManagerFactoryBean instance
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.healthcare.mgnt.entity");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> jpaProperties = new HashMap<>();
        // Fix: handle missing property source gracefully
        Object propertySourceObj = ((org.springframework.core.env.AbstractEnvironment) env).getPropertySources().stream()
                .filter(ps -> ps.getName().contains("applicationConfig: [classpath:/application.properties]"))
                .findFirst()
                .map(ps -> ps.getSource())
                .orElse(null);
        if (propertySourceObj instanceof Map) {
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) propertySourceObj).entrySet()) {
                if (entry.getKey().startsWith("spring.jpa.properties.")) {
                    jpaProperties.put(entry.getKey().replace("spring.jpa.properties.", ""), entry.getValue());
                }
            }
        }
        jpaProperties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        em.setJpaPropertyMap(jpaProperties);
        return em;
    }

    /**
     * Configures the transaction manager for JPA transactions.
     * @param entityManagerFactory the JPA entity manager factory
     * @return PlatformTransactionManager instance
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
