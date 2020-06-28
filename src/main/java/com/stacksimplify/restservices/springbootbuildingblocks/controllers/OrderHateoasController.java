package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.Order;
import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.OrderRepository;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.UserRepository;

@RestController
@Validated
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{userid}/orders")
	public ResponseEntity<List<Order>> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		java.util.Optional<User> userOptional = userRepository.findById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		List<Order> allorders = userOptional.get().getOrders();
		return new ResponseEntity<>(allorders, HttpStatus.OK);

	}
}
