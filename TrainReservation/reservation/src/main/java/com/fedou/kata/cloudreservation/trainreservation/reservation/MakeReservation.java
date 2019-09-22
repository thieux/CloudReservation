package com.fedou.kata.cloudreservation.trainreservation.reservation;

import com.fedou.kata.cloudreservation.trainreservation.bookingreference.BookingReferenceService;
import com.fedou.kata.cloudreservation.trainreservation.traindata.SeatData;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MakeReservation {
    private TrainDataService trainDataService;
    private BookingReferenceService bookingReferenceService;

    @Autowired
    public MakeReservation(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.trainDataService = trainDataService;
        this.bookingReferenceService = bookingReferenceService;
    }

    public Reservation book(String trainId, int numberOfSeats) {
        TrainData seats = trainDataService.getTrainData(trainId);
        String bookingReference = bookingReferenceService.getUniqueBookingReference();
        List<String> bookedSeats = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            SeatData seat = seats.seats[i];
            bookedSeats.add(seat.getName());
        }
        trainDataService.reserve(
                trainId,
                bookedSeats,
                bookingReference);
        return new Reservation(trainId, bookingReference, bookedSeats);
    }
}
