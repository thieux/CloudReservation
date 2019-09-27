package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class Coach {
    private final String coachId;
    private final LinkedList<Integer> freeSeats;

    public Coach(String coachId, List<Integer> seats) {
        this.coachId = coachId;
        freeSeats = new LinkedList<>();
        freeSeats.addAll(seats);
    }

    List<String> findSeatsForBooking(int numberOfSeats) {
        if (freeSeats.size()<numberOfSeats) {
            return emptyList();
        }
        return freeSeats.subList(0, numberOfSeats)
                .stream().map((item) -> item+coachId).collect(Collectors.toList());
    }
}
