package com.outfit7.fun7gamebackend.contoller;

import com.outfit7.fun7gamebackend.controller.ServicesController;
import com.outfit7.fun7gamebackend.model.ServiceStatus;
import com.outfit7.fun7gamebackend.model.User;
import com.outfit7.fun7gamebackend.service.AdsService;
import com.outfit7.fun7gamebackend.service.CustomerSupportService;
import com.outfit7.fun7gamebackend.service.MultiplayerService;
import com.outfit7.fun7gamebackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ServicesControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private MultiplayerService multiplayerService;

    @Mock
    private CustomerSupportService customerSupportService;

    @Mock
    private AdsService adsService;

    @InjectMocks
    private ServicesController servicesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckServices_AllEnabled() {
        User user = User.builder()
                .userId("user9")
                .countryCode("US")
                .numberOfGamePlays(9).build();
        String timezone = "Europe/Ljubljana";
        when(userService.getApiCallsForUser(anyString())).thenReturn(user.getNumberOfGamePlays());
        when(multiplayerService.isMultiplayerEnabled(anyInt(), anyString(), anyString())).thenReturn(true);
        when(customerSupportService.isCustomerSupportEnabled(anyString())).thenReturn(true);
        when(adsService.areAdsEnabled(anyString())).thenReturn(true);

        ResponseEntity<ServiceStatus> responseEntity = servicesController
                .checkServices(timezone, user.getUserId(), user.getCountryCode());
        ServiceStatus serviceStatus = responseEntity.getBody();

        Assertions.assertNotNull(serviceStatus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("enabled", serviceStatus.getMultiplayer());
        assertEquals("enabled", serviceStatus.getUserSupport());
        assertEquals("enabled", serviceStatus.getAds());
    }

    @Test
    void testCheckServices_MultiplayerDisabled() {
        User user = User.builder()
                .userId("user10")
                .countryCode("US")
                .numberOfGamePlays(3).build();
        String timezone = "Europe/Ljubljana";
        when(userService.getApiCallsForUser(anyString())).thenReturn(user.getNumberOfGamePlays()); // Less than 5 API calls
        when(multiplayerService.isMultiplayerEnabled(anyInt(), anyString(), anyString())).thenReturn(false);
        when(customerSupportService.isCustomerSupportEnabled(anyString())).thenReturn(true);
        when(adsService.areAdsEnabled(anyString())).thenReturn(true);

        ResponseEntity<ServiceStatus> responseEntity = servicesController
                .checkServices(timezone, user.getUserId(), user.getCountryCode());
        ServiceStatus serviceStatus = responseEntity.getBody();

        Assertions.assertNotNull(serviceStatus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("disabled", serviceStatus.getMultiplayer());
        assertEquals("enabled", serviceStatus.getUserSupport());
        assertEquals("enabled", serviceStatus.getAds());
    }

    @Test
    void testCheckServices_CustomerSupportDisabled() {
        User user = User.builder()
                .userId("user4")
                .countryCode("SI")
                .numberOfGamePlays(3).build();
        String timezone = "Europe/Ljubljana";
        when(userService.getApiCallsForUser(anyString())).thenReturn(user.getNumberOfGamePlays()); // More than 5 API calls
        when(multiplayerService.isMultiplayerEnabled(anyInt(), anyString(), anyString())).thenReturn(true);
        when(customerSupportService.isCustomerSupportEnabled(anyString())).thenReturn(false);
        when(adsService.areAdsEnabled(anyString())).thenReturn(true);

        ResponseEntity<ServiceStatus> responseEntity = servicesController
                .checkServices(timezone, user.getUserId(), user.getCountryCode());
        ServiceStatus serviceStatus = responseEntity.getBody();

        Assertions.assertNotNull(serviceStatus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("enabled", serviceStatus.getMultiplayer());
        assertEquals("disabled", serviceStatus.getUserSupport());
        assertEquals("enabled", serviceStatus.getAds());
    }

    @Test
    void testCheckServices_AdsDisabled() {
        User user = User.builder()
                .userId("user4")
                .countryCode("US")
                .numberOfGamePlays(3).build();
        String timezone = "Europe/Ljubljana";
        when(userService.getApiCallsForUser(anyString())).thenReturn(user.getNumberOfGamePlays()); // More than 5 API calls
        when(multiplayerService.isMultiplayerEnabled(anyInt(), anyString(), anyString())).thenReturn(true);
        when(customerSupportService.isCustomerSupportEnabled(anyString())).thenReturn(true);
        when(adsService.areAdsEnabled(anyString())).thenReturn(false);

        ResponseEntity<ServiceStatus> responseEntity = servicesController
                .checkServices(timezone, user.getUserId(), user.getCountryCode());
        ServiceStatus serviceStatus = responseEntity.getBody();

        Assertions.assertNotNull(serviceStatus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("enabled", serviceStatus.getMultiplayer());
        assertEquals("enabled", serviceStatus.getUserSupport());
        assertEquals("disabled", serviceStatus.getAds());
    }
}
