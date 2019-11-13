package com.epam.springLabEpam.service;

import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.model.User;

import java.util.List;

public interface UserService {
    void signUp(UserDto user, String password);

    User signIn(String email, String password);

    void subscribe(UserDto user);

    List<UserDto> findAll();
}
