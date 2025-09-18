package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the mapping between roles and permissions in the HealthCare Management System.
 * Associates a role with a specific permission, enabling fine-grained access control.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_permission_map", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})})
public class RolePermissionMap extends Auditable {
    /** Unique identifier for the role-permission mapping record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permission_map_id")
    private Long rolePermissionMapId;

    /** The role in this mapping. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /** The permission in this mapping. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private RolePermission permission;

}
