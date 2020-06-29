package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.services.UserService;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {
	@Autowired
	private UserService userService;

	@GetMapping("/{id}") //fields with hashset
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {
		try {

			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			MappingJacksonValue mapper = new MappingJacksonValue(user);
			Set<String> fields = new HashSet<>();
			fields.add("id");
			fields.add("userName");
			fields.add("ssn");
			fields.add("orders");
			FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));
			mapper.setFilters(filters);
			return mapper;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping("/params/{id}") //fields with @Request param
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id,
			@RequestParam Set<String> fields) {
		try {

			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			MappingJacksonValue mapper = new MappingJacksonValue(user);
			FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));
			mapper.setFilters(filters);
			return mapper;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

}
