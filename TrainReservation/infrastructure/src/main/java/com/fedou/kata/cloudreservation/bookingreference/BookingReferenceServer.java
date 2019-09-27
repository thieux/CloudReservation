package com.fedou.kata.cloudreservation.bookingreference;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@ConditionalOnProperty(name="monolith", value = "true")
public class BookingReferenceServer {
    public BookingReferenceServer() {
        System.err.println("BookingReferenceServer.BookingReferenceServer");
    }

    @RequestMapping("/booking_reference")
    public String provideUniqueUniversalId() {
        return UUID.randomUUID().toString();
    }

}
