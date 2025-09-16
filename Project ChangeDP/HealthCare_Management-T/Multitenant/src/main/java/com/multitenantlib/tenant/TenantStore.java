package com.multitenantlib.tenant;

import java.util.List;
import java.util.Optional;


public interface TenantStore {
    Optional<Tenant> findByIdentifier(String identifier);
    List<Tenant> getAll();
}