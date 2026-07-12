package com.fleetops.auth.service;

import com.fleetops.auth.dto.LoginRequestDTO;
import com.fleetops.auth.dto.LoginResponseDTO;

public interface AuthService {
  LoginResponseDTO login(LoginRequestDTO request);
}
