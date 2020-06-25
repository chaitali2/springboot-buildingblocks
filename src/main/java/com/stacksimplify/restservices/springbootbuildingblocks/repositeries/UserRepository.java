package com.stacksimplify.restservices.springbootbuildingblocks.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String username);
}
