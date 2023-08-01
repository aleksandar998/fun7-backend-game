package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.exception.UserNotFoundException;
import com.outfit7.fun7gamebackend.model.User;
import com.outfit7.fun7gamebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " was not found"));
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " was not found"));
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public int getApiCallsForUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " was not found"));
        return user.getNumberOfGamePlays();
    }
}
