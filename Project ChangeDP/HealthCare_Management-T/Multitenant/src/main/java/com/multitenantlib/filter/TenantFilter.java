package com.multitenantlib.filter;

import com.multitenantlib.context.TenantContext;
import com.multitenantlib.resolver.TenantResolver;
import com.multitenantlib.tenant.TenantStore;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet filter for resolving and setting the tenant context for each request.
 * Handles tenant identification and context management, with robust logging and exception handling.
 */
public class TenantFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TenantFilter.class);

    private final List<TenantResolver> resolvers;
    private final TenantStore tenantStore;

    /**
     * Constructs the TenantFilter with resolvers and tenant store.
     * @param resolvers list of tenant resolvers
     * @param tenantStore tenant store
     */
    public TenantFilter(List<TenantResolver> resolvers, TenantStore tenantStore) {
        this.resolvers = resolvers;
        this.tenantStore = tenantStore;
    }

    /**
     * Filters incoming requests to resolve and set tenant context.
     * Logs resolution attempts and handles exceptions robustly.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        logger.debug("TenantFilter: Filtering request URI: {}", request.getRequestURI());
        try {
            Optional<String> identifier = Optional.empty();
            for (TenantResolver r : resolvers) {
                identifier = r.resolveTenantIdentifier(request);
                logger.debug("Attempted tenant resolution with {}: {}", r.getClass().getSimpleName(), identifier.orElse("none"));
                if (identifier.isPresent()) break;
            }
            if (identifier.isPresent()) {
                String id = identifier.get();
                logger.info("Tenant identifier resolved: {}", id);
                tenantStore.findByIdentifier(id).ifPresentOrElse(
                    t -> {
                        logger.info("Tenant found: {}. Setting context to schema: {}", t.getIdentifier(), t.getSchema());
                        TenantContext.setCurrentTenant(t.getSchema());
                    },
                    () -> logger.warn("Tenant not found for identifier: {}", id)
                );
            } else {
                logger.warn("No tenant identifier resolved for request URI: {}", request.getRequestURI());
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Exception in TenantFilter for URI: " + request.getRequestURI(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Tenant resolution error");
        } finally {
            logger.debug("Clearing tenant context after request.");
            TenantContext.clear();
        }
    }
}