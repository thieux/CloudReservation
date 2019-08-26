package com.fedou.kata.cloudreservation.trainreservation.traindata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
@Primary
public class TrainDataRemote implements TrainDataService {
    private RestTemplate restTemplate;

    @Value("${trainDataUrl}")
    private String trainDataUrl;

    @Autowired
    public TrainDataRemote(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public TrainData getTrainData(String trainId) {
        return null;
    }

    @Override
    public void reserve(String trainId, List<String> seats, String bookingReference) {

    }
}
