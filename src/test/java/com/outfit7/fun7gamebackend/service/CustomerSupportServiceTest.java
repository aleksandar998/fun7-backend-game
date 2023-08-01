package com.outfit7.fun7gamebackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerSupportServiceTest {
    @Test
    void testCustomerSupportEnabled_DuringWorkingHours() {
        CustomerSupportService customerSupportService = new CustomerSupportServiceImpl();
        // Set the user's timezone to Ljubljana (working hours from 9:00 to 15:00)
        String userTimezone = "Europe/Ljubljana";
        // Set the current time to be during working hours
        ZonedDateTime currentTime = ZonedDateTime.of(2023, 5, 30, 12, 30, 0, 0, ZoneId.of(userTimezone));

        boolean isCustomerSupportEnabled = customerSupportService.isCustomerSupportEnabled(currentTime.getZone().getId());
        assertTrue(isCustomerSupportEnabled, "Customer support should be enabled during working hours");
    }
}
