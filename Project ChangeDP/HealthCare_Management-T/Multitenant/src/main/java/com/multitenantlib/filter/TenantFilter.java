package com.multitenantlib.filter;

import com.multitenantlib.context.TenantContext;
import com.multitenantlib.resolver.TenantResolver;
import com.multitenantlib.tenant.TenantStore;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Order(Integer.MIN_VALUE)
public class TenantFilter implements jakarta.servlet.Filter {


    private final List<TenantResolver> resolvers;
    private final TenantStore tenantStore;


    public TenantFilter(List<TenantResolver> resolvers, TenantStore tenantStore) {
        this.resolvers = resolvers;
        this.tenantStore = tenantStore;
    }


    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            Optional<String> identifier = Optional.empty();
            for (TenantResolver r : resolvers) {
                identifier = r.resolveTenantIdentifier(request);
                if (identifier.isPresent()) break;
            }


            if (identifier.isPresent()) {
                String id = identifier.get();
                tenantStore.findByIdentifier(id).ifPresent(t -> TenantContext.setCurrentTenant(t.getSchema()));
            }


            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}