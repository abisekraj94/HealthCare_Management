package com.healthcare.mgnt.entity.user;

import com.healthcare.mgnt.entity.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user role in the HealthCare Management System.
 * Defines access levels and permissions for users, and maps to users and permissions.
 */
@Entity
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends Auditable {
    /** Unique identifier for the role. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    /** Name of the role (e.g., ADMIN, DOCTOR, NURSE). */
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    /** Description of the role. */
    @Column(name = "description")
    private String description;

    /** Users assigned to this role. */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    /** Permissions associated with this role. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "role_permission_map",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<RolePermission> permissions = new HashSet<>();

}
