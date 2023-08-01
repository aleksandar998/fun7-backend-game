package com.outfit7.fun7gamebackend.contoller;

import com.outfit7.fun7gamebackend.controller.AdminController;
import com.outfit7.fun7gamebackend.model.User;
import com.outfit7.fun7gamebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Prepare mock data
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("user1", "SI", 10));

        // Mock the service method
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Call the controller method
        ResponseEntity<List<User>> response = adminController.getAllUsers();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());

        // Verify that the service method was called once
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserDetails() {
        // Prepare mock data
        String userId = "user1";
        User mockUser = new User(userId, "SI", 10);

        // Mock the service method
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Call the controller method
        ResponseEntity<User> response = adminController.getUserDetails(userId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verify that the service method was called once
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testDeleteUser() {
        // Prepare mock data
        String userId = "user1";
        User mockUser = new User(userId, "SI", 10);

        // Mock the service method
        when(userService.deleteUser(userId)).thenReturn(mockUser);

        // Call the controller method
        ResponseEntity<User> response = adminController.deleteUser(userId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verify that the service method was called once
        verify(userService, times(1)).deleteUser(userId);
    }
}
