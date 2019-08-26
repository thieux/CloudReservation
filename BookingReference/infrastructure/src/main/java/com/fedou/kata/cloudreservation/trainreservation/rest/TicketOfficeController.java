package com.fedou.kata.cloudreservation.trainreservation.rest;

import com.fedou.kata.cloudreservation.trainreservation.reservation.MakeReservation;
import com.fedou.kata.cloudreservation.trainreservation.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/reservation")
public class TicketOfficeController {
    private MakeReservation makeReservation;

    @Autowired
    public TicketOfficeController(MakeReservation makeReservation)
    {
        this.makeReservation = makeReservation;
    }

    @RequestMapping(method = POST, path = "/makeReservation")
    public ReservationDTO makeReservation(@RequestBody ReservationRequestDTO req) {
        Reservation booking = makeReservation.book(req.getTrainId(), req.getNumberOfSeats());
        return ReservationDTO.toDTO(booking);
        //return new ReservationDTO(null, null, null);
    }
}
