package com.lamiga;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final ProductService service;
    private final EmailService emailService;

    public ApiController(ProductService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products() {
        try {
            List<Product> products = service.all();
            log.info("Products requested, returning {} items", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error fetching products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
            "status", "ok",
            "service", "la-miga-backend",
            "timestamp", java.time.Instant.now().toString()
        );
    }

    public record ContactDTO(
            @NotBlank(message = "Name is required") String name,
            @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
            @NotBlank(message = "Message is required") String message
    ) {}

    @PostMapping("/contact")
    public ResponseEntity<Map<String, Object>> contact(@Valid @RequestBody ContactDTO dto) {
        try {
            log.info("Contact form submission from: {} ({})", dto.name(), dto.email());
            
            // Сохраняем в файл (backup)
            saveToFile(dto);
            
            // Отправляем на email (если настроено)
            emailService.sendContactForm(dto.name(), dto.email(), dto.message());
            
            log.info("Contact form processed successfully for: {}", dto.email());
            return ResponseEntity.ok(Map.of("ok", true, "message", "Message received successfully"));
        } catch (IOException e) {
            log.error("Error saving contact form to file", e);
            // Пытаемся отправить email даже если файл не сохранился
            emailService.sendContactForm(dto.name(), dto.email(), dto.message());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "error", "Failed to save message"));
        } catch (Exception e) {
            log.error("Unexpected error processing contact form", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("ok", false, "error", "Internal server error"));
        }
    }

    private void saveToFile(ContactDTO dto) throws IOException {
        Path store = Paths.get("data", "contact.jsonl");
        Files.createDirectories(store.getParent());
        String json = String.format(
                "{\"ts\":\"%s\",\"name\":\"%s\",\"email\":\"%s\",\"message\":\"%s\"}%n",
                java.time.Instant.now(),
                escapeJson(dto.name()),
                escapeJson(dto.email()),
                escapeJson(dto.message())
        );
        Files.writeString(store, json, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        log.debug("Contact form saved to file: {}", store);
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ConstraintViolationException e) {
        log.warn("Validation error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("ok", false, "error", "Validation failed", "details", e.getMessage()));
    }
}