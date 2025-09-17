package com.multitenantlib.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the current tenant context using ThreadLocal.
 * Provides static methods to set, get, and clear the tenant identifier for the current thread.
 */
public class TenantContext {
    private static final Logger logger = LoggerFactory.getLogger(TenantContext.class);
    private static final ThreadLocal<String> current = new ThreadLocal<>();

    /**
     * Sets the current tenant identifier for the thread.
     * @param tenantId the tenant identifier
     */
    public static void setCurrentTenant(String tenantId) {
        logger.debug("Setting current tenant: {}", tenantId);
        current.set(tenantId);
    }

    /**
     * Gets the current tenant identifier for the thread.
     * @return the tenant identifier
     */
    public static String getCurrentTenant() {
        String tenantId = current.get();
        logger.debug("Getting current tenant: {}", tenantId);
        return tenantId;
    }

    /**
     * Clears the current tenant identifier for the thread.
     */
    public static void clear() {
        logger.debug("Clearing current tenant context.");
        current.remove();
    }
}