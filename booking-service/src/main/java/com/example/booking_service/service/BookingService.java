package com.example.booking_service.service;

import com.example.booking_service.client.InventoryServiceClient;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.product.BookingProduct;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.InventoryResponse;
import com.example.booking_service.response.BookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingProduct> kafkaTemplate;

    @Autowired
    public BookingService(final CustomerRepository customerRepository,
                          final InventoryServiceClient inventoryServiceClient,
                          final KafkaTemplate<String, BookingProduct> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // Checar se o usuário existe
        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        System.out.println("Informação do usuário: " + customer);
        if (customer == null) {
            throw new RuntimeException("User not found");
        }
        // Verificar se há quantidade suficiente
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getProductId());
        log.info("Resposta do serviço Inventory: {}", inventoryResponse);

        if (inventoryResponse.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }

        // Criar booking
        final BookingProduct bookingProduct = createBookingProduct(request, customer, inventoryResponse);
        // Enviar booking para o Kafka
        kafkaTemplate.send("bookingTopic", bookingProduct);
        log.info("Booking enviado para o Kafka: {}", bookingProduct);

        return BookingResponse.builder()
                .userId(bookingProduct.getUserId())
                .productId(bookingProduct.getProductId())
                .quantity(bookingProduct.getQuantity())
                .totalPrice(BigDecimal.valueOf(bookingProduct.getTotalPrice()))
                .build();
    }

    private BookingProduct createBookingProduct(final BookingRequest request, final Customer customer, final InventoryResponse inventoryResponse) {
        return BookingProduct.builder()
                .userId(customer.getId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(inventoryResponse.getPrice() * request.getQuantity())
                .build();
    }
}
