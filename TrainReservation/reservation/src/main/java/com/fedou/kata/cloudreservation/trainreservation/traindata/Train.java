package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

public class Train {
    private final String trainId;
    private List<Coach> coaches = new LinkedList<>();

    public Train(String trainId, List<Coach> coaches) {
        this.trainId = trainId;
        this.coaches.addAll(coaches);
    }

    public List<String> findSeatsForBooking(int numberOfSeats) {
        for (Coach current : coaches) {
            List<String> seats = current.findSeatsForBooking(numberOfSeats);
            if (!seats.isEmpty()) {
                return seats;
            }
        }
        return emptyList();
    }
}
