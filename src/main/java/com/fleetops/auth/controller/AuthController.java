package com.fleetops.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetops.auth.dto.LoginRequestDTO;
import com.fleetops.auth.dto.LoginResponseDTO;
import com.fleetops.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
	    this.authService = authService;
	}
	@PostMapping("/login")
	public LoginResponseDTO login(
	        @Valid @RequestBody LoginRequestDTO request) {

	    return authService.login(request);
	}
}
