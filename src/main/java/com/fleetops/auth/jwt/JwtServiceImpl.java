package com.fleetops.auth.jwt;

import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtServiceImpl implements JwtService {
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;
	private SecretKey getSigningKey() {
	    return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	private Claims extractAllClaims(String token) {

	    return Jwts.parser()
	            .verifyWith(getSigningKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
	private boolean isTokenExpired(String token) {

	    return extractAllClaims(token)
	            .getExpiration()
	            .before(new Date());

	}
	@Override
	public String generateToken(String username) {

	    return Jwts.builder()

	            .subject(username)

	            .issuedAt(new Date())

	            .expiration(
	                    new Date(System.currentTimeMillis() + jwtExpiration))

	            .signWith(getSigningKey())

	            .compact();
	}
	@Override
	public String extractUsername(String token) {

	    return extractAllClaims(token).getSubject();

	}
	@Override
	public boolean isTokenValid(String token) {

	    return !isTokenExpired(token);

	}
}
