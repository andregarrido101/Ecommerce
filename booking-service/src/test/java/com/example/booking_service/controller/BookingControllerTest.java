package com.example.booking_service.controller;

import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookingService bookingService;

    @Test
    void shouldCreateBooking() throws Exception {

        BookingResponse BookingResponse = new BookingResponse(1L, 1L, 10L, new BigDecimal("100.00"));

        when(bookingService.createBooking(Mockito.any())).thenReturn(BookingResponse);

        String json = """
                {
                    "userId": 1,
                    "productId": 1,
                    "quantity": 10
                }
                """;

        // Execute the request
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json)
        );

        // Verification
        result
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("productId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("totalPrice").value(100.00));
    }
}
