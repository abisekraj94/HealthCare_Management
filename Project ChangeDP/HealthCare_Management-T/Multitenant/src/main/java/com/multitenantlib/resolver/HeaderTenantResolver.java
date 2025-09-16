package com.multitenantlib.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@ConditionalOnProperty(prefix = "multitenant.resolver", name = "type", havingValue = "header", matchIfMissing = false)
public class HeaderTenantResolver implements TenantResolver {

    private final String headerName;

    public HeaderTenantResolver() {
        this.headerName = "X-Tenant-ID"; // default header
    }

    @Override
    public Optional<String> resolveTenantIdentifier(HttpServletRequest request) {
        String val = request.getHeader(headerName);
        return (val == null || val.isBlank()) ? Optional.empty() : Optional.of(val.trim());
    }
}