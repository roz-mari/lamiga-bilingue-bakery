package com.lamiga;

public record Product(
        Long id,
        String name_en,
        String name_es,
        String description_en,
        String description_es,
        double price,
        String imageUrl
) {}
