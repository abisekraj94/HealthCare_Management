package com.healthcare.mgnt.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenantId = httpRequest.getHeader("X-Tenant-ID");

        if (tenantId == null) {
            // Try to get from subdomain
            String host = httpRequest.getServerName();
            if (host.startsWith("sims")) tenantId = "SIMS";
            else if (host.startsWith("miot")) tenantId = "MIOT";
            else if (host.startsWith("apollo")) tenantId = "APOLLO";
        }

        // If still not found, try to get from request body (for JSON POST/PUT/PATCH)
        boolean isJson = httpRequest.getContentType() != null && httpRequest.getContentType().contains("application/json");
        String method = httpRequest.getMethod();
        if (tenantId == null && isJson && ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method))) {
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode body = mapper.readTree(cachedRequest.getInputStream());
                if (body.has("tenant")) {
                    tenantId = body.get("tenant").asText();
                }
            } catch (Exception e) {
                // Ignore parsing errors, fallback to default
            }
            httpRequest = cachedRequest;
        }

        if (tenantId != null) {
            TenantContext.setTenantId(tenantId.toLowerCase());
        }

        try {
            chain.doFilter(httpRequest, response);
        } finally {
            TenantContext.clear();
        }
    }
}