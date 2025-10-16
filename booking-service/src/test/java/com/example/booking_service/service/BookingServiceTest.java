package com.example.booking_service.service;

import com.example.booking_service.client.InventoryServiceClient;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.product.BookingProduct;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.InventoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryServiceClient inventoryServiceClient;

    @Mock
    KafkaTemplate kafkaTemplate;

    @Test
    void shouldCreateBooking() {

        BookingRequest bookingRequest = new BookingRequest(1L, 1L, 10L);

        InventoryResponse inventoryResponse = new InventoryResponse(1L, "Product Test", "Product test description", 100.0, 10L);

        Customer customer = new Customer(1L, "Andre", "andre@email.com");

        BookingProduct bookingProduct = new BookingProduct(1L, 1L, 10L, 1000.0, 1000.0 );

        Mockito
                .when(customerRepository.findById(Mockito.any()))
                .thenReturn(java.util.Optional.of(customer));

        Mockito
                .when(inventoryServiceClient.getInventory(Mockito.any()))
                .thenReturn(inventoryResponse);

        Mockito
                .when(kafkaTemplate.send(Mockito.any(), Mockito.any()))
                .thenReturn(null);


        var response = bookingService.createBooking(bookingRequest);
        System.out.println("Booking Response: " + response);

        Mockito.verify(customerRepository).findById(Mockito.any());
        Mockito.verify(inventoryServiceClient).getInventory(Mockito.any());
        Mockito.verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
    }
}
