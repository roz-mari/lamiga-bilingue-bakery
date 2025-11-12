package com.lamiga.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        webRequest = new ServletWebRequest(request);
    }

    @Test
    @DisplayName("Should handle ResourceNotFoundException")
    void shouldHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Product not found");
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Resource Not Found", response.getBody().error());
        assertEquals("Product not found", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    @DisplayName("Should handle ValidationException")
    void shouldHandleValidationException() {
        ValidationException ex = new ValidationException("Invalid input");
        ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Validation Failed", response.getBody().error());
        assertEquals("Invalid input", response.getBody().message());
        assertNotNull(response.getBody().details());
    }

    @Test
    @DisplayName("Should handle BusinessException")
    void shouldHandleBusinessException() {
        BusinessException ex = new BusinessException("Business rule violated");
        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Business Logic Error", response.getBody().error());
        assertEquals("Business rule violated", response.getBody().message());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException")
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgumentException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Invalid Argument", response.getBody().error());
        assertEquals("Invalid argument", response.getBody().message());
    }
}

