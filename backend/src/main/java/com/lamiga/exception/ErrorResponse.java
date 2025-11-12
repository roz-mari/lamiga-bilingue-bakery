package com.lamiga.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, Object> details
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path, null);
    }

    public ErrorResponse(int status, String error, String message, String path, Map<String, Object> details) {
        this(Instant.now(), status, error, message, path, details);
    }

    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(status, error, message, path);
    }

    public static ErrorResponse of(int status, String error, String message, String path, Map<String, Object> details) {
        return new ErrorResponse(status, error, message, path, details);
    }
}

