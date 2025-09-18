package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the HealthCare Management System.
 * Stores authentication, contact, and profile information, and maps to roles for access control.
 */
@Entity
@Table(name = "\"user\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {
    /** Unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /** Username for login and identification. */
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    /** Hashed password for authentication. */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /** Email address of the user. */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /** First name of the user. */
    @Column(name = "first_name", length = 100)
    private String firstName;

    /** Last name of the user. */
    @Column(name = "last_name", length = 100)
    private String lastName;

    /** Phone number of the user. */
    @Column(name = "phone", length = 20)
    private String phone;

    /** URL to the user's profile picture. */
    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    /** Indicates if the user account is active. */
    @Column(name = "is_active")
    private Boolean isActive = true;

    /** Timestamp when the user account was created. */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /** Timestamp when the user account was last updated. */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /** Roles assigned to the user for access control. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
