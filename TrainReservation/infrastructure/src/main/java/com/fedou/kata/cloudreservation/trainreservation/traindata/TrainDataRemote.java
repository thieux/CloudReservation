package com.fedou.kata.cloudreservation.trainreservation.traindata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
@Primary
public class TrainDataRemote implements TrainDataService {
    private RestTemplate restTemplate;

    @Value("${trainDataHost}")
    private String trainDataHost;

    @Value("${trainDataEndPoint}")
    private String trainDataEndPoint;

    @Value("${trainDataBookingEndPoint}")
    private String trainDataBookingEndPoint;

    @Autowired
    public TrainDataRemote(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public TrainData getTrainData(String trainId) {
        return restTemplate.getForObject(trainDataHost + "/" + trainDataEndPoint + "/" + trainId, TrainData.class);
    }

    @Override
    public void reserve(String trainId, List<String> seats, String bookingReference) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("trainId", trainId);
        map.add("seats", seats);
        map.add("bookingId", bookingReference);
        restTemplate.postForLocation(trainDataHost + "/" + trainDataBookingEndPoint, map);
//                new TrainReservationDTO(trainId, seats, bookingReference));
    }
}
