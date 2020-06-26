package com.stacksimplify.restservices.springbootbuildingblocks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserExistException;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) throws UserExistException {
		User existingUser = userRepository.findByUserName(user.getUserName());
		if (existingUser != null) {
			throw new UserExistException("User already exist in repository");
		}
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not found in user repository");
		}
		return user;
	}

	public User updateByUserId(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not found in user repository,provide correct user id");
		}
		user.setId(id);
		return userRepository.save(user);
	}

	public void deleteUserById(Long id) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not found in user repository,provide correct user id");
		}
		userRepository.deleteById(id);
	}

	public User getUserByUserName(String username) throws UserNameNotFoundException {
		return userRepository.findByUserName(username);
	}
}
