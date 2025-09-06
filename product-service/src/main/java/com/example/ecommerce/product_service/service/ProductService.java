package com.example.ecommerce.product_service.service;

import com.example.ecommerce.product_service.entity.Product;
import com.example.ecommerce.product_service.repository.ProductRepository;
import com.example.ecommerce.product_service.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        final List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build()).collect(Collectors.toList());
    }

    public ProductResponse getProductById(final Long id) {
        final Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
