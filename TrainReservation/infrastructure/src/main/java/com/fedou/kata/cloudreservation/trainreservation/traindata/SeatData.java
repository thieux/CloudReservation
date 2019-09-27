package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.Objects;

public class SeatData {
    // {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"}
    String bookingReference;
    int seatNumber;
    String coach;

    public SeatData(){};
    public SeatData(String bookingReference, String coach, int seatNumber) {
        this.bookingReference = bookingReference;
        this.seatNumber = seatNumber;
        this.coach = coach;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getCoach() {
        return coach;
    }

    public String getName() {
        return ""+seatNumber+coach;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Seat{");
        sb.append("bookingReference='").append(bookingReference).append('\'');
        sb.append(", seatNumber=").append(seatNumber);
        sb.append(", coach='").append(coach).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatData seatData = (SeatData) o;
        return seatNumber == seatData.seatNumber &&
                Objects.equals(coach, seatData.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber, coach);
    }
}
