package com.fleetops.users.dto;

import com.fleetops.users.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
	private Long id;

    private String name;

    private String email;

    private Role role;
}
