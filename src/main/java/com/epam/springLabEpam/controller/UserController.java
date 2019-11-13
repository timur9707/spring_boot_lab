package com.epam.springLabEpam.controller;


import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.model.User;
import com.epam.springLabEpam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signUp")
    public void signUp(UserDto user, String password) {
        userService.signUp(user, password);
    }

    @GetMapping("/user/signIn")
    public User signIn(String email, String password) {
        return userService.signIn(email, password);
    }

    @PutMapping("/user/subscribe")
    public void subscribe(UserDto user) {
        userService.subscribe(user);
    }

    @GetMapping("/showAllUsers")
    public List<UserDto> showAll() {
        return userService.findAll();
    }
}
