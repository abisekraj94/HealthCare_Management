package com.multitenantlib.config;

import com.multitenantlib.hibernate.CurrentTenantIdentifierResolverImpl;
import com.multitenantlib.hibernate.SchemaMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class MultiTenantAutoConfiguration {
    @Autowired(required = false)
    private SchemaMultiTenantConnectionProviderImpl multiTenantConnectionProvider;


    @Autowired(required = false)
    private CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver;


    @Bean
    @ConditionalOnMissingBean
    public SchemaMultiTenantConnectionProviderImpl multiTenantConnectionProvider(DataSource ds) {
        return new SchemaMultiTenantConnectionProviderImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.yourapp.entities"); // will be overridden by application if needed
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProps = new HashMap<>();
        jpaProps.put("hibernate.multiTenancy", "SCHEMA");
        jpaProps.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        jpaProps.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver);
        jpaProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        emf.setJpaPropertyMap(jpaProps);
        return emf;
    }
}