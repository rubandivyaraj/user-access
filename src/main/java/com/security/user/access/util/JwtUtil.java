package com.security.user.access.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {


    @Getter
    private String[] publicUrls = {
            "/user/v1/auth/login",
            "/user/v1/auth/signup",
            "/user/v1/auth/activate",
            "/api-docs/**",
            "/swagger-ui/**",
            "/actuator/**",
    };

    @Getter
    private String[] privateUrls = {
            "/user/v1/auth/validate",
            "/user/v1/auth/deactivate",
            "/user/v1/auth/delete",
            "/user/v1/auth/logout",
            "/api/product"
    };

    private static SecretKey secretKey;
    @Value("${jwt.token.expiration}")
    private long expiration;

    public String generateToken(String username) {
        return Jwts.builder().claims().subject(username).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration)).and().signWith(secretKey).compact();
    }

    public Claims verifyToken(String token) {
        return (Claims) Jwts.parser().verifyWith(secretKey).build().parse(token).getPayload();
    }

    @PostConstruct
    private SecretKey getKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // Specify the key size
            secretKey = keyGen.generateKey();

            // Print the secret key in Base64 format
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            log.info("Generated Secret Key (Base64): {}", encodedKey);

        } catch (NoSuchAlgorithmException e) {
            log.error("Generated Secret Key (Base64): {}", e);
        }
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getEncoded()));
    }

    public long getExpireTimestamp() {
        Instant now = Instant.now();
        return now.plusSeconds(expiration).toEpochMilli();
    }

}
