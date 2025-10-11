package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.entity.Product;
import com.ecommerce.inventory_service.repository.ProductRepository;
import com.ecommerce.inventory_service.repository.RoomRepository;
import com.ecommerce.inventory_service.response.ProductInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public InventoryService(final ProductRepository productRepository, final RoomRepository room1Repository) {
        this.productRepository = productRepository;
        this.roomRepository = room1Repository;
    }

    public List<ProductInventoryResponse> getAllProducts() {
        final List<Product> products = productRepository.findAll();

        return products.stream().map(product -> ProductInventoryResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build()).toList();
    }

    public ProductInventoryResponse getProductInventory(final Long productId) {
        final Product product = productRepository.findById(productId).orElse(null);

        return ProductInventoryResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
