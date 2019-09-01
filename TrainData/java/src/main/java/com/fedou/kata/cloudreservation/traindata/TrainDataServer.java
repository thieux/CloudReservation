package com.fedou.kata.cloudreservation.traindata;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.IntStream.rangeClosed;

@RestController
public class TrainDataServer {

    private HashMap<String, TrainDataDTO> trainDatasById;

    public TrainDataServer() {
        trainDatasById = new HashMap<>();
        addAcceptanceTrain();
    }

    private void addAcceptanceTrain() {
        TrainDataDTO trainData = new TrainDataDTO();
        ArrayList<SeatDataDTO> seatDatas = new ArrayList<>();

        for (String coach : new String[]{"A", "B"}) {
            for (int seatNumber : rangeClosed(1, 10).toArray()) {
                seatDatas.add(
                        new SeatDataDTO(
                                null,
                                coach,
                                seatNumber));
            }
            ;
        }

        trainData.seats = seatDatas.toArray(new SeatDataDTO[seatDatas.size()]);
        trainDatasById.put("express_2000", trainData);
    }

    @RequestMapping("data_for_train/{trainId}")
    public TrainDataDTO getTrainDataById(@PathVariable String trainId) {
        TrainDataDTO trainData = trainDatasById.get(trainId);
        System.out.println("trainId = " + trainId);
        System.out.println("trainData = " + trainData);
        return trainData;
    }

    ;

    @RequestMapping(method = RequestMethod.POST, path = "/reserve", consumes = "application/json")
    public void reserve(@RequestBody Map<String, Object> arguments) {
        TrainDataDTO trainData = trainDatasById.get(arguments.get("trainId"));
        for (String seat : (List<String>) arguments.get("seats")) {
            String[] tokens = seat.split("");
            SeatDataDTO bookingSeat = new SeatDataDTO(
                    (String) arguments.get("bookingReference"),
                    tokens[1],
                    Integer.parseInt(tokens[0]));
            for (int i = 0; i < trainData.seats.length; i++) {
                SeatDataDTO trainSeat = trainData.seats[i];
                if (trainSeat.coach.equalsIgnoreCase(bookingSeat.coach)
                        && trainSeat.seatNumber == bookingSeat.seatNumber) {
                    trainData.seats[i] = bookingSeat;
                    System.err.println("seat found " + bookingSeat);
                }
            }
        }
    }
}
