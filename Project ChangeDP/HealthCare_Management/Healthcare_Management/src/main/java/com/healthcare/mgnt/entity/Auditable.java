package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Base entity class for auditing creation and modification details.
 * Stores information about who created/modified the entity and when.
 * Used as a superclass for entities requiring audit tracking in the HealthCare Management System.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    /** The username or identifier of the user who created the entity. */
    @Column(name = "created_by")
    private String createdBy;

    /** The username or identifier of the user who last modified the entity. */
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /** Timestamp when the entity was created. */
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    /** Timestamp when the entity was last modified. */
    @Column(name = "modified_time")
    private Instant modifiedTime;

    /**
     * JPA lifecycle callback to set creation and modification timestamps before persisting.
     */
    @PrePersist
    protected void onCreate() {
        createdTime = Instant.now();
        modifiedTime = Instant.now();
    }

    /**
     * JPA lifecycle callback to update modification timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        modifiedTime = Instant.now();
    }

}
