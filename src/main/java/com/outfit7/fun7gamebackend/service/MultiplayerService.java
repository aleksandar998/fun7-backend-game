package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplayerService {

    private final UserService userService;

    @Autowired
    public MultiplayerService(UserService userService) {
        this.userService = userService;
    }

    // Method to check if Multiplayer service is enabled for a user
    public boolean isMultiplayerEnabled(int numberOfApiCalls, String userId, String countryCode) {
        return hasEnoughApiCalls(numberOfApiCalls) && isUserFromUS(userId, countryCode);
    }

    // Helper method to check if a user has made enough API calls (more than 5 times)
    private boolean hasEnoughApiCalls(int numberOfApiCalls) {
        return numberOfApiCalls > 5;
    }

    // Helper method to check if a user is from the US
    private boolean isUserFromUS(String userId, String countryCode) {
        User user = userService.getUserById(userId);
        return user.getCountryCode().equalsIgnoreCase(countryCode);
    }
}
