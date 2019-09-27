package com.fedou.kata.cloudreservation.traindata;

import com.fedou.kata.cloudreservation.trainreservation.traindata.SeatData;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

@RestController
@ConditionalOnProperty(name="monolith", value = "true")
public class TrainDataServer {

    private final HashMap<String, TrainData> trainDatasById;

    public TrainDataServer() {
        trainDatasById = new HashMap<>();
        addAcceptanceTrain();
        System.err.println("TrainDataServer.TrainDataServer");
    }

    private void addAcceptanceTrain() {
        TrainData trainData = new TrainData();
        ArrayList<SeatData> seatDatas = new ArrayList<>();
        for (String coach: new String[]{"A", "B"}) {
            IntStream.of(10).forEach(seatNumber -> {
                    seatDatas.add(
                            new SeatData(
                                    null,
                                    coach,
                                    seatNumber));
            });
        }

        trainData.seats = (SeatData[]) seatDatas.toArray();
        trainDatasById.put("express_2000", trainData);
    }

    @RequestMapping("data_for_train/{trainId}")
    public TrainData getTrainDataById(@RequestParam String trainId) {
        return trainDatasById.get(trainId);
    }

    @RequestMapping(method = RequestMethod.POST, path="/reserve")
    public void reserve(
            @RequestBody String trainId,
            @RequestBody String[] seats,
            @RequestBody String bookingReference) {
        TrainData trainData = trainDatasById.get(trainId);
        for (String seat : seats) {
            SeatData bookingSeat = new SeatData(
                    bookingReference,
                    "" + seat.charAt(1),
                    seat.charAt(0));
            for (int i = 0; i < trainData.seats.length; i++) {
                SeatData trainSeat = trainData.seats[i];
                if (trainSeat.equals(bookingSeat)) {
                    trainData.seats[i] = bookingSeat;
                }
            }
        }
    }
}
