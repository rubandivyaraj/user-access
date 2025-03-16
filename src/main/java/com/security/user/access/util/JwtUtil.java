package com.security.user.access.util;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.token.expiration}")
	private long expiration;

	private static SecretKey secretKey;

	public String generateToken(String username) {
		System.out.println(expiration);
		return Jwts.builder().claims().subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration)).and().signWith(secretKey).compact();
	}

	public Claims verifyToken(String token) {
		System.out.println("Verify token..");
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
			System.out.println("Generated Secret Key (Base64): " + encodedKey);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getEncoded()));
	}

	public long getExpireTimestamp() {
		Instant now = Instant.now();
		return now.plusSeconds(expiration).toEpochMilli();
	}

}
