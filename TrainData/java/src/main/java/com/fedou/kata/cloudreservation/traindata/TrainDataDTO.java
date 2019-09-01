package com.fedou.kata.cloudreservation.traindata;

import java.util.Arrays;

public class TrainDataDTO {
    //     {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"}, "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}
    public SeatDataDTO[] seats;

    public SeatDataDTO[] getSeats() {
        return seats;
    }

    public void setSeats(SeatDataDTO[] seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrainData{");
        sb.append("seats=").append(seats == null ? "null" : Arrays.asList(seats).toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainDataDTO that = (TrainDataDTO) o;
        return Arrays.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(seats);
    }
}
