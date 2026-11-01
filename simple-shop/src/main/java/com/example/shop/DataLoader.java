package com.example.shop;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/** Seeds the in-memory database with sample products on startup. */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() > 0) return;
            repo.save(new Product("Wireless Headphones", "Bluetooth over-ear headphones with 30h battery.", new BigDecimal("2499.00"), "https://picsum.photos/seed/headphones/400/300"));
            repo.save(new Product("Smart Watch", "Fitness tracker with heart-rate monitor.", new BigDecimal("3999.00"), "https://picsum.photos/seed/watch/400/300"));
            repo.save(new Product("Running Shoes", "Lightweight and comfortable daily trainers.", new BigDecimal("1999.00"), "https://picsum.photos/seed/shoes/400/300"));
            repo.save(new Product("Backpack", "Water-resistant 25L laptop backpack.", new BigDecimal("1299.00"), "https://picsum.photos/seed/backpack/400/300"));
            repo.save(new Product("Coffee Mug", "Ceramic mug, 350ml, dishwasher safe.", new BigDecimal("349.00"), "https://picsum.photos/seed/mug/400/300"));
            repo.save(new Product("Desk Lamp", "LED lamp with adjustable brightness.", new BigDecimal("899.00"), "https://picsum.photos/seed/lamp/400/300"));
        };
    }
}
