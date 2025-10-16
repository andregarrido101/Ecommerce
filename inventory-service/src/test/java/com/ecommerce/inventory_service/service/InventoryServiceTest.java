package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.entity.Product;
import com.ecommerce.inventory_service.repository.ProductRepository;
import com.ecommerce.inventory_service.response.ProductInventoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    InventoryService service;

    @Mock
    ProductRepository repository;

    @Test
    void shouldGetAllProducts() {

        Product product = new Product("Test Product", "This is a test product", 100.0, 10L);

        Mockito
                .when(repository.findAll())
                .thenReturn(Collections.singletonList(product));

        List<ProductInventoryResponse> products = service.getAllProducts();

        Mockito.verify(repository).findAll();
    }

    @Test
    void shouldGetProductInventoryById() {

        Product product = new Product("Test Product", "This is a test product", 100.0, 10L);
        product.setId(1L);

        Mockito
                .when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(product));

        ProductInventoryResponse oneProduct = service.getProductInventory(1L);

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void shouldUpdateProductQuantity() {

        Product product = new Product("Test Product", "This is a test product", 100.0, 10L);
        product.setId(1L);

        Mockito
                .when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(product));

        service.updateProductQuantity(2L, 5L);

        Mockito.verify(repository).findById(Mockito.any());
    }
}
