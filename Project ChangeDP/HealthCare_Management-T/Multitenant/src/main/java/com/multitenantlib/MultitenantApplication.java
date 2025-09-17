package com.multitenantlib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Multitenant Spring Boot application.
 * Handles application startup and logs lifecycle events.
 */
@SpringBootApplication
public class MultitenantApplication {
    private static final Logger logger = LoggerFactory.getLogger(MultitenantApplication.class);

    /**
     * Starts the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting MultitenantApplication...");
        try {
            SpringApplication.run(MultitenantApplication.class, args);
            logger.info("MultitenantApplication started successfully.");
        } catch (Exception e) {
            logger.error("Application failed to start.", e);
            throw e;
        }
    }
}
