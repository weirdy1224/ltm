package com.examly.springapp.service;

import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Map<String, Object> loginUser(LoginDTO loginDTO);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long userId);
}
