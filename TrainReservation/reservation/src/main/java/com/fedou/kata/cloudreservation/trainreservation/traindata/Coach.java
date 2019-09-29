package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class Coach {
    private final String coachId;
    private final int totalSeats;
    private final List<Integer> freeSeats;

    // @see this(String coachId, int totalSeats, List<Integer> freeSeats)
    @Deprecated
    public Coach(String coachId, List<Integer> seats) {
        this(coachId, seats.size(), seats);
    }

    public Coach(String coachId, int totalSeats, List<Integer> freeSeats) {
        this.coachId = coachId;
        this.totalSeats = totalSeats;
        this.freeSeats = new LinkedList<>(freeSeats);
    }

    List<String> findSeatsForBooking(int numberOfSeats) {
        if (freeSeats.size()<numberOfSeats) {
            return emptyList();
        }
        return freeSeats.subList(0, numberOfSeats)
                .stream().map((item) -> item+coachId).collect(Collectors.toList());
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getTotalFreeSeats() {
        return freeSeats.size();
    }
}
