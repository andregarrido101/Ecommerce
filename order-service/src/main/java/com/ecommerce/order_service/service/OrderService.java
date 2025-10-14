package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.InventoryServiceClient;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.example.booking_service.product.BookingProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public OrderService(OrderRepository orderRepository, InventoryServiceClient inventoryServiceClient) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @KafkaListener(topics = "bookingTopic", groupId = "order-service")
    public void consumeBookingProduct(BookingProduct bookingProduct) {
        log.info("Received booking event: {}", bookingProduct);

        // Create order based on booking event
        Order order = createOrderFromBooking(bookingProduct);

        // Save order to database
        orderRepository.saveAndFlush(order);

        // Update inventory
        inventoryServiceClient.updateInventory(order.getProductId(), order.getQuantity());
        log.info("Inventory updated for productId: {}, quantity: {}", bookingProduct.getProductId(), bookingProduct.getQuantity());
    }

    private Order createOrderFromBooking(BookingProduct bookingProduct) {
        return Order.builder()
            .customerId(bookingProduct.getUserId())
            .productId(bookingProduct.getProductId())
            .quantity(bookingProduct.getQuantity())
            .totalPrice(bookingProduct.getTotalPrice())
            .build();
    }
}
