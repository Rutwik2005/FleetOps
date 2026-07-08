package com.fleetops.users.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleetops.users.dto.UserRequestDTO;
import com.fleetops.users.dto.UserResponseDTO;
import com.fleetops.users.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  
  public UserController(UserService userService) {
	this.userService = userService;
  }
  @PostMapping
  public UserResponseDTO createUser(
          @Valid @RequestBody UserRequestDTO request) {

      return userService.createUser(request);
  }
}
