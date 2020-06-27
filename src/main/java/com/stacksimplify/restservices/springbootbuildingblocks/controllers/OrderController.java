package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.Order;
import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.OrderRepository;
import com.stacksimplify.restservices.springbootbuildingblocks.repositeries.UserRepository;
import com.sun.el.stream.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private EntityManager entityManager;

	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		java.util.Optional<User> userOptional = userRepository.findById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		return userOptional.get().getOrders();
	}

	@PostMapping("/{userid}/orders")
	public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {
		java.util.Optional<User> userOptional = userRepository.findById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}

		User user = userOptional.get();
		order.setUser(user);
		return orderRepository.save(order);
	}

	@GetMapping("/{userid}/orders/{orderid}")
	public Order getOrderByOrderId(@PathVariable Long userid, @PathVariable Long orderid) throws UserNotFoundException {

		java.util.Optional<User> userOptional = userRepository.findById(userid);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}

		java.util.Optional<Order> orderOptional = orderRepository.findById(orderid);
		if (!orderOptional.isPresent()) {
			throw new UserNotFoundException("Order Not Found");
		}

		TypedQuery<Order> query = entityManager
				.createQuery("from Order order where"
						+ " order.orderid=" + orderid + 
						" and order.user.id=" + userid,Order.class);
		Order obj = query.getSingleResult();
		return obj;
	}
}
