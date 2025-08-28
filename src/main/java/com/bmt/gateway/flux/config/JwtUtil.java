package com.bmt.gateway.flux.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/*@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-secret-key-saurabh-kumar-tyagi-140288";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
}*/
@Component
public class JwtUtil {

    // Same key should be used in auth-service for token creation
    private static final String SECRET_KEY = "your-secret-key-saurabh-kumar-tyagi-140288";

    private Key getSignKey() {
        //byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT Signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

