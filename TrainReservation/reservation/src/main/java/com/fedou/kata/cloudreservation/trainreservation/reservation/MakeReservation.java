package com.fedou.kata.cloudreservation.trainreservation.reservation;

import com.fedou.kata.cloudreservation.trainreservation.bookingreference.BookingReferenceService;
import com.fedou.kata.cloudreservation.trainreservation.traindata.Train;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Service
public class MakeReservation {
    private TrainDataService trainDataService;
    private BookingReferenceService bookingReferenceService;
    private final Reservation failed_reservation = new Reservation(null, null, emptyList());

    @Autowired
    public MakeReservation(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.trainDataService = trainDataService;
        this.bookingReferenceService = bookingReferenceService;
    }

    public Reservation book(String trainId, int numberOfSeats) {
        if (numberOfSeats<=0) {
            return failed_reservation;
        }
        Train train = trainDataService.getTrainById(trainId);
        List<String> seatIds = train.findSeatsForBooking(numberOfSeats);

        String reference = bookingReferenceService.getUniqueBookingReference();
        trainDataService.reserve(trainId, reference, seatIds);
        return new Reservation(trainId, reference, seatIds);
    }
}
