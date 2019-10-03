package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.*;

import static java.util.Arrays.stream;

public class TrainBuilder {
    private Map<String, List<Integer>> coachesBuilder = new HashMap<>();
    private String trainId;

    public TrainBuilder(String trainId) {
        this.trainId = trainId;
    }

    public TrainBuilder with(TrainData trainData) {
        stream(trainData.seats).forEach(this::with);
        return this;
    }

    public TrainBuilder with(SeatData seat) {
        if(seat.bookingReference == null) {
            addFreeSeat(seat.coach, seat.seatNumber);
        }
        return this;
    }

    private void addFreeSeat(String coach, int seatNumber) {
        List<Integer> seats = coachesBuilder.computeIfAbsent(coach, key -> new LinkedList<>());
        seats.add(seatNumber);
    }

    public Train build() {
        ArrayList<Coach> coaches = new ArrayList<>();
        this.coachesBuilder.forEach(
                (key, value) -> coaches.add(new Coach(key, value.size(), value)));
        return new Train(trainId, coaches);
    }
}
