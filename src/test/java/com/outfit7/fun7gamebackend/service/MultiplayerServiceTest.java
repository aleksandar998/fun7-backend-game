package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class MultiplayerServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private MultiplayerService multiplayerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMultiplayerEnabled_UserFromUS_And_EnoughApiCalls() {
        String userId = "user1";
        String countryCode = "US";
        int numberOfApiCalls = 10;

        when(userService.getUserById(userId)).thenReturn(User.builder().countryCode("US").build());

        boolean isMultiplayerEnabled = multiplayerService.isMultiplayerEnabled(numberOfApiCalls, userId, countryCode);

        assertTrue(isMultiplayerEnabled, "enabled");
    }

    @Test
    void testMultiplayerDisabled_UserNotFromUS_And_EnoughApiCalls() {
        String userId = "user2";
        String countryCode = "SI";
        int numberOfApiCalls = 10;

        when(userService.getUserById(userId)).thenReturn(User.builder().countryCode("SI").build());

        boolean isMultiplayerEnabled = multiplayerService.isMultiplayerEnabled(numberOfApiCalls, userId, countryCode);

        assertTrue(isMultiplayerEnabled, "disabled");
    }

    @Test
    void testMultiplayerDisabled_UserFromUS_ButNotEnoughApiCalls() {
        String userId = "user3";
        String countryCode = "US";
        int numberOfApiCalls = 3;

        when(userService.getUserById(userId)).thenReturn(User.builder().countryCode("US").build());

        boolean isMultiplayerEnabled = multiplayerService.isMultiplayerEnabled(numberOfApiCalls, userId, countryCode);

        assertFalse(isMultiplayerEnabled, "enabled");
    }
}
