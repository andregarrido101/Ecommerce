package com.example.ecommerce.product_service;


import com.example.ecommerce.product_service.controller.ProductController;
import com.example.ecommerce.product_service.response.ProductResponse;
import com.example.ecommerce.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// Test product controller using mockito
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private static ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAllProducts")
    void getAllProductsTest() throws IOException {
        // Mocking the service layer
        List<ProductResponse> mockProductList = List.of(
                ProductResponse.builder().id(1L).name("Product 1").description("Description 1").price(10.0).build(),
                ProductResponse.builder().id(2L).name("Product 2").description("Description 2").price(20.0).build()
        );

        when(productService.getAllProducts()).thenReturn(mockProductList);

        // Calling the controller method
        List<ProductResponse> productList = productController.getProducts();

        // Assertions
        assertEquals(2, productList.size());
        assertEquals("Product 1", productList.get(0).getName());
        assertEquals("Product 2", productList.get(1).getName());

        System.out.println(productList);
    }
}
