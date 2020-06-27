package com.stacksimplify.restservices.springbootbuildingblocks.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
