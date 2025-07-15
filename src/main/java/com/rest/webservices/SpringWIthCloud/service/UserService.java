package com.rest.webservices.SpringWIthCloud.service;

import com.rest.webservices.SpringWIthCloud.repository.UserDAORepository;
import com.rest.webservices.SpringWIthCloud.user.User;
import com.rest.webservices.SpringWIthCloud.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAORepository userRepo;
    public UserService(UserDAORepository userRepo) {
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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.getId()).toUri();
        //		return ResponseEntity.created(null).build();
        return ResponseEntity.created(location ).build();
    }
}
