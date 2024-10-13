package br.com.riume.backendeureka.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        if (secret.getBytes().length < 64) {
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        }
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            return (username != null && expirationDate != null && now.before(expirationDate));
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return (claims != null) ? claims.getSubject() : null;
    }
}
