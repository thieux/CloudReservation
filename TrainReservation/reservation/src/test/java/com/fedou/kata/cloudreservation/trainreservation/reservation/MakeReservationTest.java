package com.fedou.kata.cloudreservation.trainreservation.reservation;

import com.fedou.kata.cloudreservation.trainreservation.bookingreference.BookingReferenceService;
import com.fedou.kata.cloudreservation.trainreservation.traindata.Coach;
import com.fedou.kata.cloudreservation.trainreservation.traindata.Train;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakeReservationTest {
    @Mock
    TrainDataService trainDataService;
    @Mock
    BookingReferenceService bookingReferenceService;

    @InjectMocks
    MakeReservation service;

    @Test
    void should_fail_booking_for_no_seats() {
        String trainId = "trainId";
        String bookingReference = "resa";
//        doReturn(new TrainData())
//                .when(trainDataService).getTrainData(trainId);
        Reservation reservation = service.book(trainId, 0);

        assertAll(
                () -> assertThat(reservation)
                        .isEqualToComparingFieldByField(new Reservation(null, null, emptyList())),
                () -> verifyZeroInteractions(trainDataService)
        );
    }

    @Test
    void should_book_available_seats() {
        String trainId = "trainId";
        String bookingReference = "resa";
        doReturn(new Train(trainId, singletonList(new Coach("A", asList(3, 5)))))
                .when(trainDataService).getTrainById(trainId);
        doReturn(bookingReference)
                .when(bookingReferenceService).getUniqueBookingReference();

        Reservation reservation = service.book(trainId, 1);

        assertAll(
                () -> verify(trainDataService).reserve(trainId, bookingReference, singletonList("3A")),
                () -> assertThat(reservation)
                        .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                () -> assertThat(reservation.getSeats())
                        .contains("3A")
        );
    }

    @Test
    void should_fail_booking_when_no_seat_found() {
        String trainId = "trainId";
        String bookingReference = "resa";
        doReturn(new Train(trainId, singletonList(new Coach("A", emptyList()))))
                .when(trainDataService).getTrainById(trainId);

        Reservation reservation = service.book(trainId, 1);

        assertAll(
                () -> verify(trainDataService, VerificationModeFactory.times(0)).reserve(anyString(), anyString(), anyList()),
                () -> assertThat(reservation)
                        .isEqualToIgnoringNullFields(new Reservation(trainId, "", null)),
                () -> assertThat(reservation.getSeats()).isEmpty()
        );
    }

    @Test
    void should_book_sibling_seats() {
        String trainId = "trainId";
        String bookingReference = "resa";
        doReturn(new Train(trainId, singletonList(new Coach("A", asList(1, 3, 4, 5)))))
                .when(trainDataService).getTrainById(trainId);
        doReturn(bookingReference)
                .when(bookingReferenceService).getUniqueBookingReference();

        Reservation reservation = service.book(trainId, 2);

        String[] bookedSeats = {"1A", "3A"};
        assertAll(
                () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                () -> assertThat(reservation)
                        .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                () -> assertThat(reservation.getSeats())
                        .containsExactlyInAnyOrder(bookedSeats)
        );
    }


}