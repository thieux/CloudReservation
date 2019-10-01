package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fedou.kata.cloudreservation.trainreservation.traindata.RatioCalculation.keepsUnder70PercentAfterBookingWith;
import static java.util.Collections.emptyList;

public class Coach {
    private final String coachId;
    private final int totalSeats;
    private final List<Integer> freeSeats;
    static final BookingOption emptyOption = new BookingOption(emptyList(), false);

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

    BookingOption findSeatsForBooking(int numberOfSeats) {
        if (bookingFitsInCoach(numberOfSeats)) {
            return new BookingOption(
                    collectSeatsForBooking(numberOfSeats),
                    keepsUnder70PercentAfterBookingWith(totalSeats, freeSeats.size(), numberOfSeats));
        }
        return emptyOption;
    }

    static class BookingOption {
        final List<String> seats;
        final boolean respectFreeSeatsRatio;
        BookingOption (List<String> seats, boolean respectFreeSeatsRatio) {
            this.seats = seats;
            this.respectFreeSeatsRatio = respectFreeSeatsRatio;
        }

        boolean seatsFound() {
            return !seats.isEmpty();
        }
    }

    private List<String> collectSeatsForBooking(int numberOfSeats) {
        return freeSeats.subList(0, numberOfSeats)
                .stream().map((item) -> item + coachId).collect(Collectors.toList());
    }

    private boolean bookingFitsInCoach(int numberOfSeats) {
        return freeSeats.size() >= numberOfSeats;
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
