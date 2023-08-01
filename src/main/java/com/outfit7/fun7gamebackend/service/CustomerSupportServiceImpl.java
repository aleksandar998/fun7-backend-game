package com.outfit7.fun7gamebackend.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class CustomerSupportServiceImpl implements CustomerSupportService {
    @Override
    public boolean isCustomerSupportEnabled(String userTimezone) {
        // Get the current time in the user's timezone
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of(userTimezone));

        // Check if it's a work day (Monday to Friday) and within working hours (9:00 - 15:00)
        DayOfWeek dayOfWeek = currentTime.getDayOfWeek();
        int hour = currentTime.getHour();
        boolean isWorkDay = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
        boolean isWorkingHours = hour >= 9 && hour < 15;

        // Enable customer support only if it's the Ljubljana working hours
        return isWorkDay && isWorkingHours;
    }
}
