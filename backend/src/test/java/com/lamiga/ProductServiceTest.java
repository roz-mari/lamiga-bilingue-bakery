package com.lamiga;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DisplayName("ProductService Tests")
class ProductServiceTest {

    private final ProductService productService = new ProductService();

    @Test
    @DisplayName("Should return all products")
    void shouldReturnAllProducts() {
        List<Product> products = productService.all();
        
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(5, products.size());
    }

    @Test
    @DisplayName("Should return products with correct structure")
    void shouldReturnProductsWithCorrectStructure() {
        List<Product> products = productService.all();
        
        products.forEach(product -> {
            assertNotNull(product.id());
            assertNotNull(product.name_en());
            assertNotNull(product.name_es());
            assertNotNull(product.description_en());
            assertNotNull(product.description_es());
            assertTrue(product.price() > 0);
            assertNotNull(product.imageUrl());
        });
    }

    @Test
    @DisplayName("Should contain expected products")
    void shouldContainExpectedProducts() {
        List<Product> products = productService.all();
        
        assertTrue(products.stream().anyMatch(p -> p.name_en().equals("Sourdough Bread")));
        assertTrue(products.stream().anyMatch(p -> p.name_en().equals("Croissant")));
        assertTrue(products.stream().anyMatch(p -> p.name_en().equals("Baguette")));
        assertTrue(products.stream().anyMatch(p -> p.name_en().equals("Chocolate Chip Cookie")));
        assertTrue(products.stream().anyMatch(p -> p.name_en().equals("Cinnamon Roll")));
    }

    @Test
    @DisplayName("Should have unique product IDs")
    void shouldHaveUniqueProductIds() {
        List<Product> products = productService.all();
        
        long uniqueIds = products.stream()
                .map(Product::id)
                .distinct()
                .count();
        
        assertEquals(products.size(), uniqueIds);
    }
}

