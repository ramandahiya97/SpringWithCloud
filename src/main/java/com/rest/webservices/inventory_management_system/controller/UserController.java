package com.rest.webservices.inventory_management_system.controller;

import java.util.List;

import com.rest.webservices.inventory_management_system.model.User;
import com.rest.webservices.inventory_management_system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/user")
public class UserController {

	private final UserService userService;
	@Autowired
	public UserController(UserService userService){this.userService = userService;}

	@GetMapping()
	public List<User> retrieveAllUsers(){
		log.info(" Entered retrieve all user method");
		return userService.findAll();
	}
	@GetMapping("/{id}")
	public User findUser(@PathVariable int id) {
		log.info("Entered findUser method");
		return userService.findOne(id);
	}
	
	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Entered CreateUser method");
		return userService.create(user);
	}
}
