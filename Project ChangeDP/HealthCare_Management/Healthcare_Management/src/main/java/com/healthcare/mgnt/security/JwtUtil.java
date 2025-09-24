package com.healthcare.mgnt.security;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.exception.AuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * Utility class for JWT token operations
 * Handles token generation, validation, and extraction of claims
 */
@Component
public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private final SecretKey secretKey;
    private final long jwtExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpiration = expiration;
    }

    /**
     * Generate JWT token for user
     *
     * @param userId the user ID
     * @param email the user email
     * @param roles the user roles
     * @return generated JWT token
     */
    public String generateToken(Long userId, String email, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract email from JWT token
     *
     * @param token the JWT token
     * @return email from token
     */
    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * Extract user ID from JWT token
     *
     * @param token the JWT token
     * @return user ID from token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * Extract roles from JWT token
     *
     * @param token the JWT token
     * @return roles from token
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (List<String>) claims.get("roles");
    }

    /**
     * Validate JWT token
     *
     * @param token the JWT token to validate
     * @throws AuthException if token is invalid or expired
     */
    public void validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims.getExpiration().before(new Date())) {
                throw new AuthException(AppErrorCodes.TOKEN_HAS_EXPIRED);
            }
        } catch (ExpiredJwtException e) {
            throw new AuthException(AppErrorCodes.TOKEN_HAS_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AuthException(AppErrorCodes.TOKEN_HAS_INVALID);
        }
    }

    /**
     * Extract claims from JWT token
     *
     * @param token the JWT token
     * @return claims from token
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}