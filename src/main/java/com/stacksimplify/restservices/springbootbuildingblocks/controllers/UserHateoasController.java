package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserExistException;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.UserRepository;
import com.stacksimplify.restservices.springbootbuildingblocks.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/hateoas/users")
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		List<User> allusers = userService.getAllUsers();
		for (User user : allusers) {
			Long userid = user.getId();
			// with self link
			user.add(linkTo(methodOn(UserHateoasController.class).getUserById(userid)).withSelfRel());
			// realtionship with orders
			user.add(linkTo(methodOn(OrderHateoasController.class).getAllOrders(userid)).withRel("all-orders"));
		}
		Link selflink = linkTo(methodOn(UserHateoasController.class).getAllUsers()).withSelfRel();
		CollectionModel<User> result = new CollectionModel<>(allusers, selflink);
		return result;
		
//		return new ResponseEntity<>(allusers, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			user.add(linkTo(methodOn(UserHateoasController.class).getUserById(id)).withSelfRel());

			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}
