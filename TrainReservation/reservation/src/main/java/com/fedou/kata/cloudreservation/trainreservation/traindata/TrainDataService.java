package com.fedou.kata.cloudreservation.trainreservation.traindata;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainDataService {

    void reserve(String trainId, String bookingReference, List<String> seats);

    Train getTrainById(String trainId);
}
