package com.lamiga;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> items = List.of(
            new Product(1L, "Sourdough Bread", "Pan de masa madre",
                    "Naturally leavened bread with crispy crust",
                    "Pan de masa madre con corteza crujiente",
                    3.50, "/img/sourdough.jpg"),
            new Product(2L, "Croissant", "Croissant",
                    "Buttery and flaky croissant",
                    "Croissant mantecoso y hojaldrado",
                    1.80, "/img/croissant.jpg"),
            new Product(3L, "Baguette", "Baguette",
                    "Crispy outside, soft inside",
                    "Crujiente por fuera, suave por dentro",
                    1.60, "/img/baguette.jpg"),
            new Product(4L, "Chocolate Chip Cookie", "Galleta con chocolate",
                    "Classic cookie with chocolate chips",
                    "Galleta clásica con trozos de chocolate",
                    1.20, "/img/cookie.jpg"),
            new Product(5L, "Cinnamon Roll", "Roll de canela",
                    "Sweet roll with cinnamon glaze",
                    "Bollito dulce con glaseado de canela",
                    2.20, "/img/cinnamon-roll.jpg")
    );

    public List<Product> all() {
        return items;
    }
}