package com.fedou.kata.cloudreservation.trainreservation.bookingreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.newBuilder;

@Repository
@Primary
public class BookingReferenceRemote implements BookingReferenceService {

    private RestTemplate restTemplate;

    @Value("${bookingReferenceUrl}")
    private String bookingReferenceUrl;

    @Autowired
    public BookingReferenceRemote(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getUniqueBookingReference() {
        return restTemplate.getForObject(bookingReferenceUrl, String.class);
    }
}
