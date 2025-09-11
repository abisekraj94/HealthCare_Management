package com.multitenant.core;

public class HeaderTenantResolver implements TenantResolver {
    private final String headerName;

    public HeaderTenantResolver(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String resolveTenantId(jakarta.servlet.http.HttpServletRequest request) {
        return request.getHeader(headerName);
    }
}