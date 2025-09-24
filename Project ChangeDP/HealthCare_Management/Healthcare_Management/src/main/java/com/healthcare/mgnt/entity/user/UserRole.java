package com.healthcare.mgnt.entity.user;

import com.healthcare.mgnt.entity.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

/**
 * Entity representing the mapping between users and roles in the HealthCare Management System.
 * Associates a user with a specific role for access control purposes.
 */
@Entity
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
public class UserRole extends Auditable {
    /** Unique identifier for the user-role mapping record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long userRoleId;

    /** The user in this mapping. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The role in this mapping. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
