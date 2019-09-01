package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.List;
import java.util.Objects;

class TrainReservationDTO {
    private final String trainId;
    private final List<String> seats;
    private final String bookingReference;

    TrainReservationDTO(String trainId, List<String> seats, String bookingReference) {
        this.trainId = trainId;
        this.seats = seats;
        this.bookingReference = bookingReference;
    }

    public String getTrainId() {
        return trainId;
    }

    public List<String> getSeats() {
        return seats;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrainReservationDTO{");
        sb.append("trainId='").append(trainId).append('\'');
        sb.append(", seats=").append(seats);
        sb.append(", bookingReference='").append(bookingReference).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainReservationDTO that = (TrainReservationDTO) o;
        return trainId.equals(that.trainId) &&
                seats.equals(that.seats) &&
                bookingReference.equals(that.bookingReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, seats, bookingReference);
    }
}
