package com.fedou.kata.cloudreservation.trainreservation.rest;

import java.util.Objects;

public class ReservationRequestDTO {
    private String trainId;
    private int numberOfSeats;

    public ReservationRequestDTO() {}

    public ReservationRequestDTO(String trainId, int numberOfSeats) {
        this.trainId = trainId;
        this.numberOfSeats = numberOfSeats;
    }

    public String getTrainId() {
        return trainId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequestDTO that = (ReservationRequestDTO) o;
        return numberOfSeats == that.numberOfSeats &&
                Objects.equals(trainId, that.trainId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(trainId, numberOfSeats);
    }
}
