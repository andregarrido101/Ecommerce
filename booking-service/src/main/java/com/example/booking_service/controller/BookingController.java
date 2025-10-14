package com.example.booking_service.controller;

import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService reservationService;

    @Autowired
    public BookingController(BookingService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/booking")
    public BookingResponse createBooking(@RequestBody BookingRequest request) {
        return reservationService.createBooking(request);
    }
}
