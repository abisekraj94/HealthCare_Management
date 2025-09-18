package com.healthcare.mgnt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interface for loading user details for authentication.
 */
public interface ICustomUserDetailsService {
    /**
     * Loads user details by username for authentication.
     * @param username the username to look up
     * @return UserDetails for Spring Security authentication
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

