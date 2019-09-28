package com.fedou.kata.cloudreservation.trainreservation.bookingreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@Primary
public class BookingReferenceRepository implements BookingReferenceService {

    private RestTemplate restTemplate;

    @Value("${bookingReferenceUrl}")
    private String bookingReferenceUrl;

    @Autowired
    public BookingReferenceRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getUniqueBookingReference() {
        return restTemplate.getForObject(bookingReferenceUrl, String.class);
    }
}
