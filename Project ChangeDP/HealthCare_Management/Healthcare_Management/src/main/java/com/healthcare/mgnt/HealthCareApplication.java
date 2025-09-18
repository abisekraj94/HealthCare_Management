package com.healthcare.mgnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point for the HealthCare Management application.
 */
@EnableJpaAuditing
@SpringBootApplication
public class HealthCareApplication {
    /**
     * Starts the HealthCare Management Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(HealthCareApplication.class, args);
    }
}
