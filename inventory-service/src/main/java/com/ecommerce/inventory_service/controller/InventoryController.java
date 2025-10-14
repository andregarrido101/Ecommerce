package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.response.ProductInventoryResponse;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryService inventoryService;


    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/test")
    public String test() {
        return "Inventory Service is up and running!";
    }

    @GetMapping("/inventory/products")
    public @ResponseBody List<ProductInventoryResponse> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @GetMapping("/inventory/product/{productId}")
    public @ResponseBody ProductInventoryResponse inventoryForProduct(@PathVariable("productId") Long productId) {
        return inventoryService.getProductInventory(productId);
    }

    @PutMapping("/inventory/product/{productId}/quantity/{quantity}")
    public ResponseEntity<Void> updateProductQuantity(@PathVariable("productId") Long productId,
                                                      @PathVariable("quantity") Long quantity) {
        inventoryService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
