package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.response.ProductInventoryResponse;
import com.ecommerce.inventory_service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    InventoryService inventoryService;

    @Test
    void shouldGetAllProducts() throws Exception {

        ProductInventoryResponse product1 = new ProductInventoryResponse(1L, "Test Product", "This is a test product", 100.0, 10L);
        ProductInventoryResponse product2 = new ProductInventoryResponse(2L, "Another Product", "This is another test product", 150.0, 5L);
        ProductInventoryResponse product3 = new ProductInventoryResponse(3L, "Third Product", "This is the third test product", 200.0, 20L);

        when(inventoryService.getAllProducts()).thenReturn(
                java.util.List.of(product1, product2, product3)
        );

        // Execute the request
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/inventory/products")
        );

        // Verification
        result
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void shouldGetProductById() throws Exception {

        when(inventoryService.getProductInventory(1L)).thenReturn(
                new ProductInventoryResponse(1L, "Test Product", "This is a test product", 100.0, 10L)
        );

        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/inventory/product/1")
        ).andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
         .andExpect(MockMvcResultMatchers.jsonPath("name").value("Test Product"))
         .andExpect(MockMvcResultMatchers.jsonPath("description").value("This is a test product"))
         .andExpect(MockMvcResultMatchers.jsonPath("price").value(100.0))
         .andExpect(MockMvcResultMatchers.jsonPath("quantity").value(10));
    }
}
