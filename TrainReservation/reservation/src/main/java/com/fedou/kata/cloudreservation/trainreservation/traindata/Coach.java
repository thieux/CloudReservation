package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fedou.kata.cloudreservation.trainreservation.traindata.RatioCalculation.isUnder70PercentWhenBookingOf;
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

    List<String> findIdealSeatsForBooking(int numberOfSeats) {
        return findSeatsForBookingWith70PercentRule(numberOfSeats, true);
    }

    List<String> findSeatsForBooking(int numberOfSeats) {
        return findSeatsForBookingWith70PercentRule(numberOfSeats, false);
    }

    private List<String> findSeatsForBookingWith70PercentRule(int numberOfSeats, boolean idealBooking) {
        if (freeSeats.size() >= numberOfSeats) {
            if (!idealBooking || isUnder70PercentWhenBookingOf(totalSeats, freeSeats.size(), numberOfSeats)) {
                return freeSeats.subList(0, numberOfSeats)
                        .stream().map((item) -> item + coachId).collect(Collectors.toList());
            }
        }
        return emptyList();
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
