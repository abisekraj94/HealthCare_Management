package com.multitenantlib.tenant;

public class Tenant {
    private String id;
    private String identifier; // e.g. sims, miot, apollo
    private String name;
    private String connectionString; // optional - for extensibility
    private String schema; // schema name (for per-schema strategy)
    private String aCustomProperty;

    public Tenant() {}

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getConnectionString() { return connectionString; }
    public void setConnectionString(String connectionString) { this.connectionString = connectionString; }
    public String getSchema() { return schema; }
    public void setSchema(String schema) { this.schema = schema; }
    public String getaCustomProperty() { return aCustomProperty; }
    public void setaCustomProperty(String aCustomProperty) { this.aCustomProperty = aCustomProperty; }
}