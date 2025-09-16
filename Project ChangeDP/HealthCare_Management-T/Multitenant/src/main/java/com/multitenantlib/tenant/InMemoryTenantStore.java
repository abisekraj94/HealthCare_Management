package com.multitenantlib.tenant;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
@ConditionalOnProperty(prefix = "multitenant.store", name = "type", havingValue = "in-memory", matchIfMissing = true)
public class InMemoryTenantStore implements TenantStore {
    private final Map<String, Tenant> tenants = new HashMap<>();

    @PostConstruct
    public void init() {
// Defaults (sims, miot, apollo)
        Tenant sims = new Tenant();
        sims.setId(UUID.randomUUID().toString());
        sims.setIdentifier("sims");
        sims.setName("Sims Company");
        sims.setSchema("sims");
        sims.setaCustomProperty("VIP Customer");


        Tenant miot = new Tenant();
        miot.setId(UUID.randomUUID().toString());
        miot.setIdentifier("miot");
        miot.setName("Miot Company");
        miot.setSchema("miot");


        Tenant apollo = new Tenant();
        apollo.setId(UUID.randomUUID().toString());
        apollo.setIdentifier("apollo");
        apollo.setName("Apollo Company");
        apollo.setSchema("apollo");


        tenants.put(sims.getIdentifier(), sims);
        tenants.put(miot.getIdentifier(), miot);
        tenants.put(apollo.getIdentifier(), apollo);
    }


    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        return Optional.ofNullable(tenants.get(identifier));
    }


    @Override
    public List<Tenant> getAll() {
        return new ArrayList<>(tenants.values());
    }
}