package com.multitenantlib.tenant;

import java.util.List;
import java.util.Optional;

/**
 * TenantStore defines the contract for tenant storage and lookup in the multi-tenancy system.
 * Implementations provide mechanisms to find tenants by identifier and retrieve all tenants.
 */
public interface TenantStore {
    /**
     * Finds a tenant by its unique identifier.
     * @param identifier the tenant identifier
     * @return Optional containing Tenant if found, otherwise empty
     */
    Optional<Tenant> findByIdentifier(String identifier);

    /**
     * Retrieves all tenants managed by the store.
     * @return List of all tenants
     */
    List<Tenant> getAll();
}