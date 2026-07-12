package com.fleetops.users.mapper;

import java.time.LocalDateTime;


import org.springframework.stereotype.Component;

import com.fleetops.users.dto.UserRequestDTO;
import com.fleetops.users.dto.UserResponseDTO;
import com.fleetops.users.entity.Role;
import com.fleetops.users.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO request)
    {
    	User user = new User();
    	
    	user.setName(request.getName());
    	user.setEmail(request.getEmail());

    	
    	user.setRole(Role.DISPATCHER);
    	
    	return user;
    }
    public UserResponseDTO toResponse(User user) {

        UserResponseDTO response = new UserResponseDTO();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }
}
