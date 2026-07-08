package com.fleetops.users.service.impl;

import org.springframework.stereotype.Service;

import com.fleetops.common.exception.EmailAlreadyExistsException;
import com.fleetops.users.dto.UserRequestDTO;
import com.fleetops.users.dto.UserResponseDTO;
import com.fleetops.users.entity.User;
import com.fleetops.users.mapper.UserMapper;
import com.fleetops.users.repository.UserRepository;
import com.fleetops.users.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}


	@Override
    public UserResponseDTO createUser(UserRequestDTO request) {
    	if (userRepository.existsByEmail(request.getEmail())) {
    	    throw new EmailAlreadyExistsException("Email already exists");
    	}
    	User user = userMapper.toEntity(request);
    	
    	User savedUser = userRepository.save(user);
    	
    	return userMapper.toResponse(savedUser);
    }

}