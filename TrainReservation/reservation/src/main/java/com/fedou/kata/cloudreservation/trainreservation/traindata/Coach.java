package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fedou.kata.cloudreservation.trainreservation.traindata.RatioCalculation.isAbove70PercentWhenBookingOf;
import static java.util.Collections.emptyList;

public class Coach {
    private final String coachId;
    private final int totalSeats;
    private final List<Integer> freeSeats;

    public Coach(String coachId, int totalSeats, List<Integer> freeSeats) {
        this.coachId = coachId;
        this.totalSeats = totalSeats;
        this.freeSeats = new LinkedList<>(freeSeats);
    }

    List<String> findSeatsForBooking(int numberOfSeats, boolean respectFreeSeatsRatioPerCoach) {
        if (fullBookingOverLoadThisCoach(numberOfSeats)) {
            return emptyList();
        }
        if (respectFreeSeatsRatioPerCoach) {
            if (isAbove70PercentWhenBookingOf(totalSeats, freeSeats.size(), numberOfSeats)) {
                return emptyList();
            }
        }
        return listOfSeats(numberOfSeats);
    }

    private boolean fullBookingOverLoadThisCoach(int numberOfSeats) {
        return numberOfSeats > freeSeats.size();
    }

    private List<String> listOfSeats(int numberOfSeats) {
        return freeSeats.subList(0, numberOfSeats)
                .stream()
                .map(this::seatNumberToSeatId)
                .collect(Collectors.toList());
    }

    private String seatNumberToSeatId(Integer item) {
        return item + coachId;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getTotalFreeSeats() {
        return freeSeats.size();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Coach{");
        sb.append("coachId='").append(coachId).append('\'');
        sb.append(", totalSeats=").append(totalSeats);
        sb.append(", ratio=").append(((float) (totalSeats - freeSeats.size()) / (float) totalSeats));
        sb.append(", freeSeats=").append(freeSeats);
        sb.append('}');
        return sb.toString();
    }
}
