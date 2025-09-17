package com.multitenantlib.hibernate;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides SQL connections for Hibernate multi-tenancy using schema-based separation.
 * Handles connection management and schema switching with robust logging and exception handling.
 */
@Component
public class SchemaMultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {
    private static final Logger logger = LoggerFactory.getLogger(SchemaMultiTenantConnectionProviderImpl.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Gets a generic SQL connection from the data source.
     * @return SQL connection
     * @throws SQLException if connection fails
     */
    @Override
    public Connection getAnyConnection() throws SQLException {
        logger.debug("Getting any SQL connection from data source.");
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to get SQL connection.", e);
            throw e;
        }
    }

    /**
     * Releases a generic SQL connection.
     * @param connection SQL connection
     * @throws SQLException if release fails
     */
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        logger.debug("Releasing SQL connection.");
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to release SQL connection.", e);
            throw e;
        }
    }

    /**
     * Gets a tenant-specific SQL connection and sets the schema.
     * @param tenantIdentifier tenant schema
     * @return SQL connection
     * @throws SQLException if connection or schema switch fails
     */
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.debug("Getting connection for tenant: {}", tenantIdentifier);
        final Connection connection = getAnyConnection();
        try {
            if (tenantIdentifier != null && !tenantIdentifier.isBlank()) {
                connection.setSchema(tenantIdentifier);
                logger.info("Set connection schema to tenant: {}", tenantIdentifier);
            }
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to set schema for tenant: {}", tenantIdentifier, e);
            connection.close();
            throw e;
        }
    }

    /**
     * Releases a tenant-specific SQL connection and resets schema.
     * @param tenantIdentifier tenant schema
     * @param connection SQL connection
     * @throws SQLException if schema reset or release fails
     */
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        logger.debug("Releasing connection for tenant: {}", tenantIdentifier);
        try {
            connection.setSchema("public");
            connection.close();
            logger.info("Reset schema to public and closed connection for tenant: {}", tenantIdentifier);
        } catch (SQLException e) {
            logger.error("Failed to reset schema or close connection for tenant: {}", tenantIdentifier, e);
            throw e;
        }
    }

    /**
     * Indicates if aggressive release is supported.
     * @return false
     */
    @Override
    public boolean supportsAggressiveRelease() {
        logger.debug("Aggressive release not supported.");
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) { return false; }

    @Override
    public <T> T unwrap(Class<T> unwrapType) { return null; }
}