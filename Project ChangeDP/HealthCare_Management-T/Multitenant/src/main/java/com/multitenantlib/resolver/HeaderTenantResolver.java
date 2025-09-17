package com.multitenantlib.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * HeaderTenantResolver resolves the tenant identifier from a specific HTTP header.
 * This class is only active when the property 'multitenant.resolver.type=header' is set.
 */
@Component
@ConditionalOnProperty(prefix = "multitenant.resolver", name = "type", havingValue = "header", matchIfMissing = false)
public class HeaderTenantResolver implements TenantResolver {
    private static final Logger logger = LoggerFactory.getLogger(HeaderTenantResolver.class);
    private static final String headerName = "X-Tenant-ID";

    /**
     * Resolves the tenant identifier from the request header 'X-Tenant-ID'.
     * Logs the resolution process and handles exceptions gracefully.
     *
     * @param request the HTTP servlet request
     * @return Optional containing tenant identifier if present, otherwise empty
     */
    @Override
    public Optional<String> resolveTenantIdentifier(HttpServletRequest request) {
        try {
            String val = request.getHeader(headerName);
            if (val == null || val.isBlank()) {
                logger.debug("Tenant header '{}' not found or blank.", headerName);
                return Optional.empty();
            }
            logger.info("Tenant identifier '{}' resolved from header.", val.trim());
            return Optional.of(val.trim());
        } catch (Exception e) {
            logger.error("Exception while resolving tenant from header: {}", headerName, e);
            return Optional.empty();
        }
    }
}
