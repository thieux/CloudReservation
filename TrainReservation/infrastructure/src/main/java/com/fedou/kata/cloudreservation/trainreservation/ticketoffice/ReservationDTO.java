package com.fedou.kata.cloudreservation.trainreservation.ticketoffice;

import com.fedou.kata.cloudreservation.trainreservation.reservation.Reservation;

import java.util.List;
import java.util.Objects;

public class ReservationDTO {
    private String train_id;
    private String booking_reference;
    private List<String> seats;

    public ReservationDTO() {}
    public ReservationDTO(String train_id, String booking_reference, List<String> seats) {
        this.train_id = train_id;
        this.booking_reference = booking_reference;
        this.seats = seats;
    }

    public String getTrain_id() {
        return train_id;
    }

    public String getBooking_reference() {
        return booking_reference;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    public void setBooking_reference(String booking_reference) {
        this.booking_reference = booking_reference;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDTO that = (ReservationDTO) o;
        return Objects.equals(train_id, that.train_id) &&
                Objects.equals(booking_reference, that.booking_reference) &&
                Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(train_id, booking_reference, seats);
    }

    public static ReservationDTO toDTO(Reservation booking) {
        return new ReservationDTO(
                booking.getTrainId(),
                booking.getBookingReference(),
                booking.getSeats()
        );
    }
}
