package com.rest.webservices.spring_with_cloud.controller;

import java.util.List;

import com.rest.webservices.spring_with_cloud.model.User;
import com.rest.webservices.spring_with_cloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	public UserController(UserService userService){this.userService = userService;}

	@GetMapping()
	public List<User> retrieveAllUsers(){
		logger.info(" Entered retrieve all user method");
		return userService.findAll();
	}
	@GetMapping("/{id}")
	public User findUser(@PathVariable int id) {
		logger.info("Entered findUser method");
		return userService.findOne(id);
	}
	
	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.info("Entered CreateUser method");
		return userService.create(user);
	}
}
