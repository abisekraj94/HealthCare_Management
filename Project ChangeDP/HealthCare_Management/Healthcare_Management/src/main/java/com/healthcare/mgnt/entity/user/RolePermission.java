package com.healthcare.mgnt.entity.user;

import com.healthcare.mgnt.entity.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a permission that can be assigned to roles in the HealthCare Management System.
 * Defines specific access rights and is mapped to roles.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_permission")
public class RolePermission extends Auditable {
    /** Unique identifier for the permission. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    /** Name of the permission (e.g., READ_PATIENT, EDIT_VISIT). */
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    /** Description of the permission. */
    @Column(name = "description")
    private String description;

    /** Roles that have this permission. */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

}