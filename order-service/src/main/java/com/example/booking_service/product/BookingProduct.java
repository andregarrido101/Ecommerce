package com.example.booking_service.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingProduct {

    private Long userId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Double totalValue;
}
