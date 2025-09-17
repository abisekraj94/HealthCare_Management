package com.multitenantlib.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * HostTenantResolver resolves the tenant identifier from the request's host (subdomain).
 * This class is only active when the property 'multitenant.resolver.type=host' is set.
 */
@Component
@ConditionalOnProperty(prefix = "multitenant.resolver", name = "type", havingValue = "host", matchIfMissing = false)
public class HostTenantResolver implements TenantResolver {
    private static final Logger logger = LoggerFactory.getLogger(HostTenantResolver.class);

    /**
     * Resolves the tenant identifier from the request's subdomain.
     * Logs the resolution process and handles exceptions gracefully.
     * @param request the HTTP servlet request
     * @return Optional containing tenant identifier if present, otherwise empty
     */
    @Override
    public Optional<String> resolveTenantIdentifier(HttpServletRequest request) {
        try {
            String host = request.getServerName(); // e.g. sims.example.com
            if (host == null) {
                logger.debug("Request host is null.");
                return Optional.empty();
            }
            String[] parts = host.split("\\.");
            if (parts.length == 0) {
                logger.debug("Host '{}' does not contain subdomain parts.", host);
                return Optional.empty();
            }
            String subdomain = parts[0];
            if (subdomain.equalsIgnoreCase("www")) {
                logger.debug("Subdomain 'www' is ignored.");
                return Optional.empty();
            }
            logger.info("Tenant identifier '{}' resolved from host.", subdomain);
            return Optional.of(subdomain);
        } catch (Exception e) {
            logger.error("Exception while resolving tenant from host.", e);
            return Optional.empty();
        }
    }
}
