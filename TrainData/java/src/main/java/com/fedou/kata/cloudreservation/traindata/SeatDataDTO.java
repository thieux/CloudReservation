package com.fedou.kata.cloudreservation.traindata;

import java.util.Objects;

public class SeatDataDTO {
    // {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"}
    String bookingReference;
    int seatNumber;
    String coach;

    public SeatDataDTO(String bookingReference, String coach, int seatNumber) {
        this.bookingReference = bookingReference;
        this.seatNumber = seatNumber;
        this.coach = coach.toUpperCase();
    }

    public SeatDataDTO() { // for Json parser needs
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
        this.coach = coach.toUpperCase();
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
        SeatDataDTO seatData = (SeatDataDTO) o;
        return seatNumber == seatData.seatNumber &&
                coach.equalsIgnoreCase(seatData.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber, coach);
    }
}
