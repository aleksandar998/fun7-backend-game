package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.exception.UserNotFoundException;
import com.outfit7.fun7gamebackend.model.User;
import com.outfit7.fun7gamebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllUsers() {
        // Prepare test data
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "SI", 10));
        users.add(new User("user2", "US", 3));

        // Mock the repository method
        when(userRepository.findAll()).thenReturn(users);

        // Call the service method
        List<User> result = userService.getAllUsers();

        // Assert the result
        assertEquals(2, result.size());
    }

    @Test
    void testGetUserById() {
        // Prepare test data
        User user = new User("user1", "SI", 10);

        // Mock the repository method
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        // Call the service method
        User result = userService.getUserById("user1");

        // Assert the result
        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        assertEquals("SI", result.getCountryCode());
        assertEquals(10, result.getNumberOfGamePlays());
    }

    @Test
    void testGetUserByIdThrowsUserNotFoundException() {
        String userId = "nonExistentUser";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser() {
        // Prepare test data
        User user = new User("user1", "SI", 10);

        // Mock the repository method
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        // Call the service method
        User result = userService.deleteUser("user1");

        // Assert the result
        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        assertEquals("SI", result.getCountryCode());
        assertEquals(10, result.getNumberOfGamePlays());

        // Verify that the deleteById method was called
        verify(userRepository, times(1)).deleteById("user1");
    }

    @Test
    void testDeleteUserThrowsUserNotFoundException() {
        String userId = "nonExistentUser";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testGetApiCallsForUser() {
        // Prepare test data
        User user = new User("user1", "SI", 10);

        // Mock the repository method
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        // Call the service method
        int result = userService.getApiCallsForUser("user1");

        // Assert the result
        assertEquals(10, result);
    }

    @Test
    void testGetApiCallsForUserThrowsUserNotFoundException() {
        String userId = "nonExistentUser";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getApiCallsForUser(userId));

        verify(userRepository, times(1)).findById(userId);
    }
}
