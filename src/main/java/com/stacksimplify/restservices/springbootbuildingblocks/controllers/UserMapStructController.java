package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.springbootbuildingblocks.dtos.UserMsDto;
import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.mappers.UserMapper;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.UserRepository;

@RestController
@RequestMapping(value = "/mapstruct/users")
public class UserMapStructController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<UserMsDto> getAllUsers() {
		return userMapper.usersToUserDtos(userRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public UserMsDto getUserById(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.get();
		return userMapper.userToUserMsDto(user);
	}
}
