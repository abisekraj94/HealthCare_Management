package com.multitenantlib.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "multitenant.resolver", name = "type", havingValue = "host", matchIfMissing = false)
public class HostTenantResolver implements TenantResolver {


    @Override
    public Optional<String> resolveTenantIdentifier(HttpServletRequest request) {
        String host = request.getServerName(); // e.g. sims.example.com
        if (host == null) return Optional.empty();
        String[] parts = host.split("\\.");
        if (parts.length == 0) return Optional.empty();
        String subdomain = parts[0];
        if (subdomain.equalsIgnoreCase("www")) return Optional.empty();
        return Optional.of(subdomain);
    }
}