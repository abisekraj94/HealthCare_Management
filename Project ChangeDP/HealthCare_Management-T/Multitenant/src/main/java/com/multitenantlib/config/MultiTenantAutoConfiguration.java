package com.multitenantlib.config;

import com.multitenantlib.hibernate.CurrentTenantIdentifierResolverImpl;
import com.multitenantlib.hibernate.SchemaMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Auto-configuration for multi-tenant support.
 * Provides beans for tenant connection provider and identifier resolver.
 */
@Configuration
public class MultiTenantAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MultiTenantAutoConfiguration.class);

    @Autowired(required = false)
    private SchemaMultiTenantConnectionProviderImpl multiTenantConnectionProvider;


    @Autowired(required = false)
    private CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver;


    /**
     * Bean for SchemaMultiTenantConnectionProviderImpl.
     */
    @Bean
    @ConditionalOnMissingBean
    public SchemaMultiTenantConnectionProviderImpl multiTenantConnectionProvider(DataSource ds) {
        logger.info("Creating SchemaMultiTenantConnectionProviderImpl bean.");
        try {
            return new SchemaMultiTenantConnectionProviderImpl();
        } catch (Exception e) {
            logger.error("Failed to create SchemaMultiTenantConnectionProviderImpl bean.", e);
            throw e;
        }
    }


    /**
     * Bean for CurrentTenantIdentifierResolverImpl.
     */
    @Bean
    @ConditionalOnMissingBean
    public CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver() {
        logger.info("Creating CurrentTenantIdentifierResolverImpl bean.");
        try {
            return new CurrentTenantIdentifierResolverImpl();
        } catch (Exception e) {
            logger.error("Failed to create CurrentTenantIdentifierResolverImpl bean.", e);
            throw e;
        }
    }

    /**
     * Bean for LocalContainerEntityManagerFactoryBean with multi-tenancy properties.
     */
    @Bean
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        logger.info("Creating LocalContainerEntityManagerFactoryBean with multi-tenancy support.");
        try {
            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            emf.setDataSource(dataSource);
            emf.setPackagesToScan("com.yourapp.entities"); // will be overridden by application if needed
            emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Map<String, Object> jpaProps = new HashMap<>();
            jpaProps.put("hibernate.multiTenancy", "SCHEMA");
            jpaProps.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
            jpaProps.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver);
            emf.setJpaPropertyMap(jpaProps);
            return emf;
        } catch (Exception e) {
            logger.error("Failed to create EntityManagerFactory bean.", e);
            throw e;
        }
    }
}