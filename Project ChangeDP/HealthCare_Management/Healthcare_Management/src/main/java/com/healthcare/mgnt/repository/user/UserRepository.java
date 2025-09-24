package com.healthcare.mgnt.repository.user;

import com.healthcare.mgnt.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom queries for users in the HealthCare Management System.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
    // Custom query methods if needed
}
