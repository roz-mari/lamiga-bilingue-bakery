package com.lamiga;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProductService service;
    private final EmailService emailService;

    public ApiController(ProductService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @GetMapping("/products")
    public List<Product> products() {
        return service.all();
    }

    public record ContactDTO(
            @NotBlank String name,
            @Email @NotBlank String email,
            @NotBlank String message
    ) {}

    @PostMapping("/contact")
    public Map<String, Object> contact(@Valid @RequestBody ContactDTO dto) throws IOException {
        // Сохраняем в файл (backup)
        Path store = Paths.get("data", "contact.jsonl");
        Files.createDirectories(store.getParent());
        String json = String.format(
                "{\"ts\":\"%s\",\"name\":\"%s\",\"email\":\"%s\",\"message\":\"%s\"}%n",
                java.time.Instant.now(),
                dto.name().replace("\"", "'"),
                dto.email().replace("\"", "'"),
                dto.message().replace("\"", "'")
        );
        Files.writeString(store, json, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        
        // Отправляем на email (если настроено)
        emailService.sendContactForm(dto.name(), dto.email(), dto.message());
        
        return Map.of("ok", true);
    }
}