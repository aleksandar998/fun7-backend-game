package com.outfit7.fun7gamebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiExceptionHandler {

    @ExceptionHandler(MissingParametersException.class)
    public ResponseEntity<String> handleMissingParametersException(MissingParametersException ex) {
        String errorMessage = "Error while checking is ADS enabled: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PartnerApiException.class)
    public ResponseEntity<String> handlePartnerApiException(PartnerApiException ex) {
        String errorMessage = "Error getting status for ADS response: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        String errorMessage = "Error causes bad credentials: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = "Error for Admin API: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
