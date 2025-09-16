package com.multitenantlib.context;

public class TenantContext {
    private static final ThreadLocal<String> current = new ThreadLocal<>();


    public static void setCurrentTenant(String tenantId) {
        current.set(tenantId);
    }


    public static String getCurrentTenant() {
        return current.get();
    }


    public static void clear() {
        current.remove();
    }
}