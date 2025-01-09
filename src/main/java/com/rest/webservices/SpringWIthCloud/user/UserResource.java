package com.rest.webservices.SpringWIthCloud.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	private UserDAOService service;
	public UserResource(UserDAOService service) {
		this.service = service;
	}
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	@GetMapping("/users/{id}")
	public User findUser(@PathVariable int id) {
		User user  = service.findOne(id);
		if(user ==null) {
			throw new UserNotFoundException("id = "+ id);
		}
		return user;
	}
	
	@PostMapping("users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User id = service.create(user);
URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.getId()).toUri();
		//		return ResponseEntity.created(null).build();
		return ResponseEntity.created(location ).build();
	}
}
