package com.healthcare.mgnt.service.Implementation.user;

import com.healthcare.mgnt.entity.user.User;
import com.healthcare.mgnt.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

/**
 * CustomUserDetailsService provides a custom implementation of Spring Security's UserDetailsService.
 * <p>
 * This service loads user details and authorities from the database for authentication purposes.
 * It is used by the security framework to validate user credentials and assign roles.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService, ICustomUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username for authentication.
     * <p>
     * Retrieves the user from the database and constructs a UserDetails object
     * with the user's roles as authorities. Throws UsernameNotFoundException if the user is not found.
     *
     * @param username the username to look up
     * @return UserDetails for Spring Security authentication
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Set<GrantedAuthority> authorities = new HashSet<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            logger.info("User loaded: {} with roles: {}", user.getUsername(), authorities);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(), authorities);
        } catch (UsernameNotFoundException ex) {
            logger.warn("User not found: {}", username);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error loading user by username {}: {}", username, ex.getMessage(), ex);
            throw new UsernameNotFoundException("Error loading user");
        }
    }
}
