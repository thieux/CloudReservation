package com.fedou.kata.cloudreservation.traindata;

import com.fedou.kata.cloudreservation.trainreservation.traindata.SeatData;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class TrainDataBuilder {
    public static TrainData theAcceptanceTrain() {
        TrainData trainData = new TrainData();
        ArrayList<SeatData> seatDatas = new ArrayList<>();
        for (String coach: new String[]{"A", "B"}) {
            IntStream.range(1, 11).forEach(seatNumber -> {
                seatDatas.add(
                        new SeatData(
                                null,
                                coach,
                                seatNumber));
            });
        }

        trainData.seats =  seatDatas.toArray(new SeatData[seatDatas.size()]);
        return trainData;
    };

}
