package com.outfit7.fun7gamebackend.controller;

import com.outfit7.fun7gamebackend.model.ServiceStatus;
import com.outfit7.fun7gamebackend.service.AdsService;
import com.outfit7.fun7gamebackend.service.CustomerSupportService;
import com.outfit7.fun7gamebackend.service.MultiplayerService;
import com.outfit7.fun7gamebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServicesController {

    private final UserService userService;
    private final MultiplayerService multiplayerService;
    private final CustomerSupportService customerSupportService;
    private final AdsService adsService;

    @Autowired
    public ServicesController(UserService userService,
                              MultiplayerService multiplayerService,
                              CustomerSupportService customerSupportService,
                              AdsService adsService) {
        this.userService = userService;
        this.multiplayerService = multiplayerService;
        this.customerSupportService = customerSupportService;
        this.adsService = adsService;
    }

    @GetMapping("/services")
    public ResponseEntity<ServiceStatus> checkServices(@RequestParam("timezone") String timezone,
                                                       @RequestParam("userId") String userId,
                                                       @RequestParam("cc") String countryCode) {
        // Get the actual number of API calls for the user from the repository
        int numberOfApiCalls = userService.getApiCallsForUser(userId);
        //  Check if the multiplayer service is enabled for the user based on the number of API calls and the user's country code
        boolean isMultiplayerEnabled = multiplayerService.isMultiplayerEnabled(numberOfApiCalls, userId, countryCode);
        // Check if customer support is enabled based on current time in Ljubljana time zone
        boolean isCustomerSupportEnabled = customerSupportService.isCustomerSupportEnabled(timezone);
        // Check if the ads service is enabled for the user based on the user's country code
        boolean areAdsEnabled = adsService.areAdsEnabled(countryCode);

        // and return the ServiceStatus object in JSON format
        ServiceStatus serviceStatus = ServiceStatus.builder()
                .multiplayer(getStatus(isMultiplayerEnabled))
                .userSupport(getStatus(isCustomerSupportEnabled))
                .ads(getStatus(areAdsEnabled)).build();

        return new ResponseEntity<>(serviceStatus, HttpStatus.OK);
    }

    private String getStatus(boolean isEnabled) {
        return isEnabled ? "enabled" : "disabled";
    }
}
