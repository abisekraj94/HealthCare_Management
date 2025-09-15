package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @Column(name = "modified_time")
    private Instant modifiedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = Instant.now();
        modifiedTime = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedTime = Instant.now();
    }

}

