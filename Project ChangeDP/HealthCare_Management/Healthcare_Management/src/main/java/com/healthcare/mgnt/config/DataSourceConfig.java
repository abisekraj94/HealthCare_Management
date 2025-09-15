package com.healthcare.mgnt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public DataSource dataSource() {
        DataSource defaultDataSource = buildDefaultDataSource();

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return TenantContext.getTenantId();
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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.healthcare.mgnt.entity");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(Map.of(
            "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "update"),
            "hibernate.dialect", env.getProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect"),
            "hibernate.show_sql", env.getProperty("spring.jpa.show-sql", "true"),
            "hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql", "true")
        ));
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
