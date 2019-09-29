package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

public class Train {
    private final String trainId;
    private int totalFreeSeats;
    private int totalSeats;
    private List<Coach> coaches = new LinkedList<>();

    public Train(String trainId, List<Coach> coaches) {
        this.trainId = trainId;
        this.coaches.addAll(coaches);
        coaches.forEach(coach -> {
            totalSeats += coach.getTotalSeats();
            totalFreeSeats += coach.getTotalFreeSeats();
        });
    }

    public List<String> findSeatsForBooking(int numberOfSeats) {
        if (keepUnder70PercentWithBookingOf(numberOfSeats)) {
            for (Coach current : coaches) {
                List<String> seats = current.findSeatsForBooking(numberOfSeats);
                if (!seats.isEmpty()) {
                    return seats;
                }
            }
        }
        return emptyList();
    }

    private boolean keepUnder70PercentWithBookingOf(int numberOfSeats) {
        if (totalSeats <= 0) return false; // should not book seats in train without seats (Fret ???)
        int actualBookedSeats = totalSeats - totalFreeSeats;
        float ratio = (float) (actualBookedSeats + numberOfSeats) / (float) totalSeats; // divide with an convert in foat afterwards is too late
        return ratio <= 0.70;
    }
}
