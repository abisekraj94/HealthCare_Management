package com.multitenantlib.resolver;

import java.util.Optional;

public interface TenantResolver {
    Optional<String> resolveTenantIdentifier(jakarta.servlet.http.HttpServletRequest request);
}