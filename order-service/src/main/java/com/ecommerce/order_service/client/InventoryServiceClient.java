package com.ecommerce.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public ResponseEntity<Void> updateInventory(final Long productId, final Long quantity) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(inventoryServiceUrl + "/product/" + productId + "/quantity/" + quantity, null);
        return ResponseEntity.ok().build();
    }
}
