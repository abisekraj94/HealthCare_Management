package com.multitenantlib.resolver;

import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

/**
 * TenantResolver defines the contract for resolving a tenant identifier from an HTTP request.
 * Implementations should extract the tenant information using various strategies (header, host, etc.).
 */
public interface TenantResolver {
    /**
     * Resolves the tenant identifier from the given HTTP servlet request.
     * @param request the HTTP servlet request
     * @return Optional containing tenant identifier if resolved, otherwise empty
     */
    Optional<String> resolveTenantIdentifier(HttpServletRequest request);
}