package com.lamiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
@DisplayName("ApiController Tests")
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private EmailService emailService;

    private List<Product> mockProducts;

    @BeforeEach
    void setUp() {
        mockProducts = List.of(
                new Product(1L, "Sourdough Bread", "Pan de masa madre",
                        "Naturally leavened bread with crispy crust",
                        "Pan de masa madre con corteza crujiente",
                        3.50, "/img/sourdough.jpg"),
                new Product(2L, "Croissant", "Croissant",
                        "Buttery and flaky croissant",
                        "Croissant mantecoso y hojaldrado",
                        1.80, "/img/croissant.jpg")
        );
    }

    @Test
    @DisplayName("GET /api/products should return list of products")
    void getProductsShouldReturnListOfProducts() throws Exception {
        when(productService.all()).thenReturn(mockProducts);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name_en").value("Sourdough Bread"))
                .andExpect(jsonPath("$[0].price").value(3.50));

        verify(productService, times(1)).all();
    }

    @Test
    @DisplayName("GET /api/health should return health status")
    void getHealthShouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.service").value("la-miga-backend"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/contact should accept valid contact form")
    void postContactShouldAcceptValidContactForm() throws Exception {
        ApiController.ContactDTO contactDTO = new ApiController.ContactDTO(
                "John Doe",
                "john@example.com",
                "Hello, I would like to order some bread."
        );

        doNothing().when(emailService).sendContactForm(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.message").value("Message received successfully"));

        verify(emailService, times(1)).sendContactForm("John Doe", "john@example.com", "Hello, I would like to order some bread.");
    }

    @Test
    @DisplayName("POST /api/contact should reject invalid email")
    void postContactShouldRejectInvalidEmail() throws Exception {
        ApiController.ContactDTO contactDTO = new ApiController.ContactDTO(
                "John Doe",
                "invalid-email",
                "Hello, I would like to order some bread."
        );

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isBadRequest());

        verify(emailService, never()).sendContactForm(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("POST /api/contact should reject empty name")
    void postContactShouldRejectEmptyName() throws Exception {
        ApiController.ContactDTO contactDTO = new ApiController.ContactDTO(
                "",
                "john@example.com",
                "Hello, I would like to order some bread."
        );

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isBadRequest());

        verify(emailService, never()).sendContactForm(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("POST /api/contact should reject empty message")
    void postContactShouldRejectEmptyMessage() throws Exception {
        ApiController.ContactDTO contactDTO = new ApiController.ContactDTO(
                "John Doe",
                "john@example.com",
                ""
        );

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isBadRequest());

        verify(emailService, never()).sendContactForm(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("POST /api/contact should reject malformed JSON")
    void postContactShouldRejectMalformedJson() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest());

        verify(emailService, never()).sendContactForm(anyString(), anyString(), anyString());
    }
}

