package com.example.auth.domain.dto;

import com.example.auth.domain.entity.Product;

public record ProductResponseDTO(String id, String name, Integer price) {
    public ProductResponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }
}
