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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private static final Path CONTACT_STORE_PATH = Paths.get("data", "contact.jsonl");
    
    private final ProductService service;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public ApiController(ProductService service, EmailService emailService, ObjectMapper objectMapper) {
        this.service = service;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products() {
        List<Product> products = service.all();
        log.info("Products requested, returning {} items", products.size());
        return ResponseEntity.ok(products);
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
    public ResponseEntity<Map<String, Object>> contact(@Valid @RequestBody ContactDTO dto) throws IOException {
        log.info("Contact form submission from: {} ({})", dto.name(), dto.email());
        
        // Save to file (backup)
        // IOException will be handled by GlobalExceptionHandler
        saveToFile(dto);
        
        // Send email (if configured)
        // EmailService doesn't throw exceptions to avoid breaking file saving
        emailService.sendContactForm(dto.name(), dto.email(), dto.message());
        
        log.info("Contact form processed successfully for: {}", dto.email());
        return ResponseEntity.ok(Map.of("ok", true, "message", "Message received successfully"));
    }

    private void saveToFile(ContactDTO dto) throws IOException {
        Files.createDirectories(CONTACT_STORE_PATH.getParent());
        
        // Use ObjectMapper for proper JSON serialization (more efficient and safer)
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("ts", java.time.Instant.now().toString());
        jsonNode.put("name", dto.name());
        jsonNode.put("email", dto.email());
        jsonNode.put("message", dto.message());
        
        String json = objectMapper.writeValueAsString(jsonNode) + System.lineSeparator();
        Files.writeString(CONTACT_STORE_PATH, json, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        log.debug("Contact form saved to file: {}", CONTACT_STORE_PATH);
    }
}