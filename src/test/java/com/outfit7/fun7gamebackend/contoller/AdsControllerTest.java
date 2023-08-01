package com.outfit7.fun7gamebackend.contoller;

import com.outfit7.fun7gamebackend.controller.AdsController;
import com.outfit7.fun7gamebackend.model.AdsStatus;
import com.outfit7.fun7gamebackend.service.AdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdsControllerTest {
    @Mock
    private AdsService adsService;
    @Mock
    private AdsController adsController;

    @BeforeEach
    public void setUp() {
        adsService = mock(AdsService.class);
        adsController = new AdsController(adsService);
    }

    @Test
    void testCheckAdsStatusEnabled() {
        String countryCode = "US";
        boolean areAdsEnabled = true;
        when(adsService.areAdsEnabled(countryCode)).thenReturn(areAdsEnabled);

        ResponseEntity<AdsStatus> response = adsController.checkAdsStatus(countryCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("sure, why not!", response.getBody().getAds());
    }

    @Test
    void testCheckAdsStatusDisabled() {
        String countryCode = "CA";
        boolean areAdsEnabled = false;
        when(adsService.areAdsEnabled(countryCode)).thenReturn(areAdsEnabled);

        ResponseEntity<AdsStatus> response = adsController.checkAdsStatus(countryCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("you shall not pass!", response.getBody().getAds());
    }
}
