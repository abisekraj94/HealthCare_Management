package com.multitenant.core;

import jakarta.servlet.http.HttpServletRequest;

public interface TenantResolver {
    String resolveTenantId(HttpServletRequest request);
}