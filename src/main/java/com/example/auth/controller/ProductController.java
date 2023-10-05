package com.example.auth.controller;

import com.example.auth.domain.dto.ProductRequestDTO;
import com.example.auth.domain.dto.ProductResponseDTO;
import com.example.auth.domain.entity.Product;
import com.example.auth.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity registerProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO);
        productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable String id) {
        ProductResponseDTO product = productService.findById(id).map(ProductResponseDTO::new).orElse(null);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> products = productService.findAll().stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok(products);
    }
}
