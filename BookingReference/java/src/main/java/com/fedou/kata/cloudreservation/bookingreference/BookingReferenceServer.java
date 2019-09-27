package com.fedou.kata.cloudreservation.bookingreference;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BookingReferenceServer {

    @RequestMapping("/booking_reference")
    public String provideUniqueUniversalId() {
        return UUID.randomUUID().toString();
    }

}
