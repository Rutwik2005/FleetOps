package com.fleetops.users.service;

import com.fleetops.users.dto.UserRequestDTO;
import com.fleetops.users.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

}