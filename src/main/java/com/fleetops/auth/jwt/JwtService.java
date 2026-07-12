package com.fleetops.auth.jwt;

public interface JwtService {
	String generateToken(String username);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}
