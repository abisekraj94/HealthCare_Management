# Multi-Tenant Library for Healthcare Management Systems

## Overview
This JAR provides reusable multi-tenancy support for Spring Boot applications using PostgreSQL (schema-per-tenant strategy). It handles tenant resolution, DataSource routing, transaction management, and JPA integration.

## Features
- Tenant resolution via headers or subdomains
- DataSource routing per tenant
- Transaction and entity management
- Profile-based configuration loading
- Minimal Spring Boot integration required

## Quick Start
1. **Add the JAR as a dependency** in your main project's `pom.xml`.
2. **Configure tenants** in `application.yml`:
   ```yaml
   multitenant:
     stores:
       configurationstore:
         defaults:
           connectionString: jdbc:postgresql://localhost:5432/defaultdb
         tenants:
           - id: tenant-1
             identifier: tenant-1
             name: Tenant 1 Company Name
             connectionString: jdbc:postgresql://localhost:5432/tenant1db
           - id: tenant-2
             identifier: tenant-2
             name: Tenant 2 Company Name
             connectionString: jdbc:postgresql://localhost:5432/tenant2db
   ```
3. **Use standard Spring Data JPA repositories and entities** in your main project.

## How It Works
- The library automatically resolves the tenant from request headers or subdomains.
- Routes all database operations to the correct tenant schema.
- Supports profile-based configuration for different environments.

## Example Entity Usage
```java
@Entity
public class Patient {
    @Id
    private Long id;
    private String name;
    // ... other fields ...
}
```

## Support
For advanced configuration, see the source code in the `config` and `core` packages.

