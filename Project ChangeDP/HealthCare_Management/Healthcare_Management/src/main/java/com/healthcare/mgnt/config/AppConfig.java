package com.healthcare.mgnt.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for application-wide beans such as ModelMapper.
 */
@Configuration
public class AppConfig {
    /**
     * Provides a ModelMapper bean for DTO/entity conversion throughout the application.
     * @return ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
