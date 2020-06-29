package com.stacksimplify.restservices.springbootbuildingblocks.dtos;

import java.util.List;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.Order;

public class UserMmDto {

	private Long id;
	private String username;
	private String firstName;
	private List<Order> orders;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}
