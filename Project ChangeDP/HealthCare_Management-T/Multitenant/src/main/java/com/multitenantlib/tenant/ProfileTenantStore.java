package com.multitenantlib.tenant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "multitenant.stores.configurationstore")
public class ProfileTenantStore implements TenantStore {


    private Defaults defaults = new Defaults();
    private List<Tenant> tenants = new ArrayList<>();


    public static class Defaults {
        private String connectionString;
        public String getConnectionString() { return connectionString; }
        public void setConnectionString(String connectionString) { this.connectionString = connectionString; }
    }


    public Defaults getDefaults() { return defaults; }
    public void setDefaults(Defaults defaults) { this.defaults = defaults; }


    public List<Tenant> getTenants() { return tenants; }
    public void setTenants(List<Tenant> tenants) { this.tenants = tenants; }


    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        return tenants.stream().filter(t -> t.getIdentifier().equalsIgnoreCase(identifier)).findFirst();
    }


    @Override
    public List<Tenant> getAll() {
        return Collections.unmodifiableList(tenants);
    }
}