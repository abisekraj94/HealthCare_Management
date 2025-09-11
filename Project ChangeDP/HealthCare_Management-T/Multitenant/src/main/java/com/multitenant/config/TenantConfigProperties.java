package com.multitenant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "multitenant.stores.configurationstore")
public class TenantConfigProperties {
    private Defaults defaults;
    private List<Tenant> tenants;

    public Defaults getDefaults() {
        return defaults;
    }

    public void setDefaults(Defaults defaults) {
        this.defaults = defaults;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public static class Defaults {
        private String connectionString;

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }
    }

    public static class Tenant {
        private String id;
        private String identifier;
        private String name;
        private String connectionString;
        private String aCustomProperty;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public String getACustomProperty() {
            return aCustomProperty;
        }

        public void setACustomProperty(String aCustomProperty) {
            this.aCustomProperty = aCustomProperty;
        }
    }
}