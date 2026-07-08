package com.fleetops.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

  @NotBlank(message="Name is required")
  private String name;
  @Email(message="Invalid email")
  @NotBlank(message="Email is required")
  private String email;
  @NotBlank(message = "Password is required")
  @Size(min=8, message="Password must be at least 8 characters")
  private String password;
}
