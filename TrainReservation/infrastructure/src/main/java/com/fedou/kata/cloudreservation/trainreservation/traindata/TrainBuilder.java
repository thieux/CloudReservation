package com.fedou.kata.cloudreservation.trainreservation.traindata;

import java.util.*;

public class TrainBuilder {
    private Map<String, List<Integer>> coachesBuilder = new HashMap<>();
    private String trainId;

    public TrainBuilder(String trainId) {
        this.trainId = trainId;
    }

    public TrainBuilder with(TrainData trainData) {
        for (int i = 0; i < trainData.seats.length; i++) {
            this.with(trainData.seats[i]);
        }
        return this;
    }

    public TrainBuilder with(SeatData seat) {
        if(seat.bookingReference == null) {
            addFreeSeat(seat.coach, seat.seatNumber);
        }
        // count all seat for 70%
        // without else : coachSeatBuilder.put(coach, coachSeatBuilder.getOrDefault(coach, 0)+1)
        return this;
    }

    private void addFreeSeat(String coach, int seatNumber) {
        List<Integer> seats = coachesBuilder.computeIfAbsent(coach, key -> new LinkedList<>());
        seats.add(seatNumber);
    }

    public Train build() {
        ArrayList<Coach> coaches = new ArrayList<>();
        this.coachesBuilder.forEach(
                (key, value) -> coaches.add(new Coach(key, value)));
        return new Train(trainId, coaches);
    }
}
