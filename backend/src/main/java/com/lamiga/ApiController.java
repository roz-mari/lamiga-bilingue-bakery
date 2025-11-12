package com.lamiga;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProductService service;

    public ApiController(ProductService service) {
        this.service = service;
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
        return Map.of("ok", true);
    }
}