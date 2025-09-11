package com.multitenant.core;

public class SubdomainTenantResolver implements TenantResolver {
    @Override
    public String resolveTenantId(jakarta.servlet.http.HttpServletRequest request) {
        String host = request.getServerName();
        String[] parts = host.split("\\.");
        return parts.length > 2 ? parts[0] : null;
    }
}