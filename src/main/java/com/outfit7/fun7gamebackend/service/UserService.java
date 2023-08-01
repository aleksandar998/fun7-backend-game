package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String userId);
    User deleteUser(String userId);
    int getApiCallsForUser(String userId);
}
