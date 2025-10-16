package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.entity.Product;
import com.ecommerce.inventory_service.repository.ProductRepository;
import com.ecommerce.inventory_service.response.ProductInventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    public List<ProductInventoryResponse> getAllProducts() {
        final List<Product> products = productRepository.findAll();

        //System.out.println("TYPE OF PRODUCTS: " + products.getClass().getName());

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

    public void updateProductQuantity(final Long productId, final Long quantity) {
        final Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.saveAndFlush(product);
            log.info("Updated product {} quantity to {}", productId, product.getQuantity());
        }
    }
}
