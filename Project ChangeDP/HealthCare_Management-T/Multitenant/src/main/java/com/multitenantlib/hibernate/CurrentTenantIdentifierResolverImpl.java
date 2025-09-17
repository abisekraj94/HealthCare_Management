package com.multitenantlib.hibernate;

import com.multitenantlib.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Resolves the current tenant identifier for Hibernate multi-tenancy.
 * Uses TenantContext to determine the active tenant.
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    private static final Logger logger = LoggerFactory.getLogger(CurrentTenantIdentifierResolverImpl.class);
    private final String DEFAULT_TENANT = "public";

    /**
     * Resolves the current tenant identifier from TenantContext.
     * Logs the resolved tenant or fallback to default.
     * @return tenant identifier
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = TenantContext.getCurrentTenant();
        String resolved = (t != null) ? t : DEFAULT_TENANT;
        logger.debug("Resolved current tenant identifier: {}", resolved);
        return resolved;
    }

    /**
     * Always validates existing sessions for multi-tenancy.
     * @return true
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        logger.debug("Validating existing current sessions for tenant context.");
        return true;
    }
}