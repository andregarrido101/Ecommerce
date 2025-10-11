package com.ecommerce.inventory_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long quantity;
}
