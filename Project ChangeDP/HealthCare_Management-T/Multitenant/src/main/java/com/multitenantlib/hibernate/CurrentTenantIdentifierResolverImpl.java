package com.multitenantlib.hibernate;

import com.multitenantlib.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;


@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private final String DEFAULT_TENANT = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = TenantContext.getCurrentTenant();
        return (t != null) ? t : DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}