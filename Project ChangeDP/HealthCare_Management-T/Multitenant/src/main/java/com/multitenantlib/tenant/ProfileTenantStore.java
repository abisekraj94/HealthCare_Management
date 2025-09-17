package com.multitenantlib.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * ProfileTenantStore loads tenant configuration from external properties (YAML/properties).
 * Only active when the 'test' profile is NOT enabled. Supports configuration binding for tenants and defaults.
 */
@Profile("!test")
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "multitenant.stores.configurationstore")
public class ProfileTenantStore implements TenantStore {
    private static final Logger logger = LoggerFactory.getLogger(ProfileTenantStore.class);
    private Defaults defaults = new Defaults();
    private List<Tenant> tenants = new ArrayList<>();

    /**
     * Defaults class holds default configuration values for tenants.
     */
    public static class Defaults {
        private String connectionString;
        /**
         * Gets the default connection string.
         * @return connection string
         */
        public String getConnectionString() { return connectionString; }
        /**
         * Sets the default connection string.
         * @param connectionString connection string
         */
        public void setConnectionString(String connectionString) { this.connectionString = connectionString; }
    }

    /**
     * Gets the default configuration values.
     * @return Defaults instance
     */
    public Defaults getDefaults() { return defaults; }
    /**
     * Sets the default configuration values.
     * @param defaults Defaults instance
     */
    public void setDefaults(Defaults defaults) { this.defaults = defaults; }

    /**
     * Gets the list of configured tenants.
     * @return list of tenants
     */
    public List<Tenant> getTenants() { return tenants; }
    /**
     * Sets the list of configured tenants.
     * @param tenants list of tenants
     */
    public void setTenants(List<Tenant> tenants) { this.tenants = tenants; }

    /**
     * Finds a tenant by its identifier (case-insensitive).
     * Logs the lookup and handles exceptions gracefully.
     * @param identifier tenant identifier
     * @return Optional containing Tenant if found, otherwise empty
     */
    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        try {
            Optional<Tenant> result = tenants.stream().filter(t -> t.getIdentifier().equalsIgnoreCase(identifier)).findFirst();
            if (result.isPresent()) {
                logger.info("Tenant '{}' found in ProfileTenantStore.", identifier);
            } else {
                logger.warn("Tenant '{}' not found in ProfileTenantStore.", identifier);
            }
            return result;
        } catch (Exception e) {
            logger.error("Exception while finding tenant by identifier: {}", identifier, e);
            return Optional.empty();
        }
    }

    /**
     * Returns all tenants as an unmodifiable list.
     * Logs the retrieval and handles exceptions gracefully.
     * @return unmodifiable list of tenants
     */
    @Override
    public List<Tenant> getAll() {
        try {
            logger.info("Retrieving all tenants from ProfileTenantStore.");
            return Collections.unmodifiableList(tenants);
        } catch (Exception e) {
            logger.error("Exception while retrieving all tenants.", e);
            return Collections.emptyList();
        }
    }
}