package com.outfit7.fun7gamebackend.controller;

import com.outfit7.fun7gamebackend.model.AdsStatus;
import com.outfit7.fun7gamebackend.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdsController {
    private final AdsService adsService;

    @Autowired
    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping("/ads")
    public ResponseEntity<AdsStatus> checkAdsStatus(@RequestParam("countryCode") String countryCode) {
        boolean areAdsEnabled = adsService.areAdsEnabled(countryCode);

        AdsStatus status = new AdsStatus();
        if (areAdsEnabled) {
            status.setAds("sure, why not!");
        } else {
            status.setAds("you shall not pass!");
        }

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
