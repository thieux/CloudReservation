package com.fedou.kata.cloudreservation.trainreservation.traindata;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainDataService {
    TrainData getTrainData(String trainId);

    void reserve(String trainId, List<String> seats, String bookingReference);
}
