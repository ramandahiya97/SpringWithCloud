package com.rest.webservices.inventory_management_system.service;

import com.rest.webservices.inventory_management_system.repository.UserRepository;
import com.rest.webservices.inventory_management_system.model.User;
import com.rest.webservices.inventory_management_system.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findOne(int id) {
        User user  = userRepo.findOne(id);
        if(user ==null) {
            throw new UserNotFoundException("id = "+ id);
        }
        return user;
    }

    public ResponseEntity<User> create(User user) {
        User id = userRepo.create(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.getId())
                .toUri();
        return ResponseEntity.created(location ).build();
    }
}
