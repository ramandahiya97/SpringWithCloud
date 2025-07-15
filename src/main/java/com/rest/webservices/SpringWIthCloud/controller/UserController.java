package com.rest.webservices.SpringWIthCloud.controller;

import java.util.List;

import com.rest.webservices.SpringWIthCloud.user.User;
import com.rest.webservices.SpringWIthCloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	public UserController(UserService userService){this.userService = userService;}

	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		logger.info(" Entered retrieve all user method");
		return userService.findAll();
	}
	@GetMapping("/users/{id}")
	public User findUser(@PathVariable int id) {
		return userService.findOne(id);
	}
	
	@PostMapping("users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return userService.create(user);
	}
}
