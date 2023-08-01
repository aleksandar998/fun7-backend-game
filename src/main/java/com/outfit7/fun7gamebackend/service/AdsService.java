package com.outfit7.fun7gamebackend.service;

import com.outfit7.fun7gamebackend.exception.MissingParametersException;
import com.outfit7.fun7gamebackend.exception.PartnerApiException;
import com.outfit7.fun7gamebackend.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AdsService {
    private final RestTemplate restTemplate;

    private static final String PARTNER_API_URL = "https://us-central1-o7tools.cloudfunctions.net/fun7-ad-partner";
    private static final String USERNAME = "fun7user";
    private static final String PASSWORD = "fun7pass";

    @Autowired
    public AdsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(USERNAME, PASSWORD)
                .build();
    }

    public boolean areAdsEnabled(String countryCode) {
        if (countryCode == null || countryCode.isEmpty()) {
            throw new MissingParametersException("Missing mandatory parameter: countryCode");
        }
        // Set up the HTTP headers with basic authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(USERNAME, PASSWORD);

        // Set up the query parameters for the HTTP request
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PARTNER_API_URL)
                .queryParam("countryCode", countryCode);

        // Create the HTTP request entity with the headers and query parameters
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Make the HTTP GET request to the partner's API
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // Handle different response statuses and error cases
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            if (responseBody != null) {
                return responseBody.contains("sure, why not!");
            }
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new UnauthorizedAccessException("Unauthorized access to the partner's API");
        } else {
            throw new PartnerApiException("Error in the partner's API response");
        }

        // Ads are disabled if the response status is not OK or if the response body contains "you shall not pass!"
        return false;
    }
}
