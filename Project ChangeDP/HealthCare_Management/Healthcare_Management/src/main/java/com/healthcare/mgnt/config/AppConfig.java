package com.healthcare.mgnt.config;

import com.healthcare.mgnt.config.TenantFilter;
import jakarta.servlet.Filter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configuration class for application-wide beans.
 */
@Configuration
public class AppConfig {
    /**
     * Provides a ModelMapper bean for DTO/entity conversion.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FilterRegistrationBean<Filter> tenantFilterRegistration() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
