package com.multitenantlib.tenant;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.*;

/**
 * InMemoryTenantStore provides a simple in-memory implementation of TenantStore for testing purposes.
 * Tenants are hardcoded and initialized at startup. Only active when 'test' profile is enabled.
 */
@Profile("test")
@Configuration
public class InMemoryTenantStore implements TenantStore {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryTenantStore.class);
    private final Map<String, Tenant> tenants = new HashMap<>();
    private static final String SIMS = "sims";
    private static final String MIOT = "miot";
    private static final String APOLLO = "apollo";

    /**
     * Initializes the in-memory tenant store with default tenants.
     * Logs the initialization process and handles exceptions gracefully.
     */
    @PostConstruct
    public void init() {
        try {
            tenants.put(SIMS, buildTenant(SIMS, "Sims Company", "sims", "VIP Customer"));
            tenants.put(MIOT, buildTenant(MIOT, "Miot Company", "miot", null));
            tenants.put(APOLLO, buildTenant(APOLLO, "Apollo Company", "apollo", null));
            logger.info("InMemoryTenantStore initialized with tenants: {}", tenants.keySet());
        } catch (Exception e) {
            logger.error("Exception during tenant store initialization.", e);
        }
    }

    /**
     * Builds a Tenant object with the provided details.
     * @param identifier tenant identifier
     * @param name tenant name
     * @param schema tenant schema
     * @param setaCustomProperty custom property
     * @return Tenant instance
     */
    private static Tenant buildTenant(String identifier, String name, String schema, String setaCustomProperty) {
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID().toString());
        tenant.setIdentifier(identifier);
        tenant.setName(name);
        tenant.setSchema(schema);
        tenant.setACustomProperty(setaCustomProperty);
        return tenant;
    }

    /**
     * Finds a tenant by its identifier.
     * @param identifier tenant identifier
     * @return Optional containing Tenant if found, otherwise empty
     */
    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        try {
            return Optional.ofNullable(tenants.get(identifier));
        } catch (Exception e) {
            logger.error("Exception while finding tenant by identifier: {}", identifier, e);
            return Optional.empty();
        }
    }

    /**
     * Returns all tenants in the store.
     * @return List of all tenants
     */
    @Override
    public List<Tenant> getAll() {
        try {
            return new ArrayList<>(tenants.values());
        } catch (Exception e) {
            logger.error("Exception while retrieving all tenants.", e);
            return Collections.emptyList();
        }
    }
}