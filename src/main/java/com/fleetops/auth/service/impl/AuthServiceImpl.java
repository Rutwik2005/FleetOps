package com.fleetops.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fleetops.auth.dto.LoginRequestDTO;
import com.fleetops.auth.dto.LoginResponseDTO;
import com.fleetops.auth.jwt.JwtService;
import com.fleetops.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {

	    Authentication authentication =
	            authenticationManager.authenticate(

	                    new UsernamePasswordAuthenticationToken(

	                            request.getEmail(),
	                            request.getPassword()));

	    String token =
	            jwtService.generateToken(request.getEmail());

	    return new LoginResponseDTO(
	            token,
	            "Bearer");
	}
}
