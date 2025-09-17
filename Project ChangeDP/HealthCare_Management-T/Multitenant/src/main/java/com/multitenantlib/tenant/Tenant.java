package com.multitenantlib.tenant;

import lombok.Data;

/**
 * Tenant represents a tenant entity in the multi-tenancy system.
 * Contains identifying information, schema, and custom properties for each tenant.
 */
@Data
public class Tenant {
    /** Unique ID for the tenant. */
    private String id;
    /** Unique identifier for the tenant (e.g. sims, miot, apollo). */
    private String identifier;
    /** Display name of the tenant. */
    private String name;
    /** Optional connection string for extensibility. */
    private String connectionString;
    /** Schema name for per-schema strategy. */
    private String schema;
    /** Custom property for tenant-specific configuration. */
    private String aCustomProperty;

    /**
     * Default constructor for Tenant.
     */
    public Tenant() {}

    /**
     * Constructs a Tenant with identifier, name, and schema.
     * @param identifier unique identifier
     * @param name display name
     * @param schema schema name
     */
    public Tenant(String identifier, String name, String schema) {
        this.identifier = identifier;
        this.name = name;
        this.schema = schema;
    }
}