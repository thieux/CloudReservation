package com.fedou.kata.cloudreservation.trainreservation.reservation;

import java.util.List;
import java.util.Objects;

public class Reservation {
    private String trainId;
    private String bookingReference;
    private List<String> seats;

    public Reservation(String trainId, String bookingReference, List<String> seats) {
        this.trainId = trainId;
        this.bookingReference = bookingReference;
        this.seats = seats;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public List<String> getSeats() {
        return seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(trainId, that.trainId) &&
                Objects.equals(bookingReference, that.bookingReference) &&
                Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, bookingReference, seats);
    }
}
