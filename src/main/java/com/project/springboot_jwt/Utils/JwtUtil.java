package com.project.springboot_jwt.Utils;

import com.project.springboot_jwt.Enitity.TokenBlacklist;
import com.project.springboot_jwt.Repository.TokenBlacklistRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final byte[] SECRET_KEY; // No longer static
    private final long EXPIRATION_TIME = 1000 * 60 * 60*24; // 1 hour

    @Autowired
    public JwtUtil(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
        this.SECRET_KEY = generateSecretKey();
    }
    // Method to generate a secure random secret key
    private static byte[] generateSecretKey() {
        // Replace this with your secure random key generation logic
        return "your_secure_secret_here".getBytes(StandardCharsets.UTF_8);
    }
    // 生成Token
    public String generateToken(String username) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("role","user")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                return false; // Token is blacklisted, hence invalid
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUserName(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    // Method to add token to blacklist
    public void addToBlacklist(String token) {
        Date expiryDate = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
        TokenBlacklist tokenBlacklist = new TokenBlacklist(token, expiryDate);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    // Method to check if token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByToken(token).isPresent();
    }
}
