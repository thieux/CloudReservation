package com.fedou.kata.cloudreservation.trainreservation.reservation;

import com.fedou.kata.cloudreservation.trainreservation.bookingreference.BookingReferenceService;
import com.fedou.kata.cloudreservation.trainreservation.traindata.Coach;
import com.fedou.kata.cloudreservation.trainreservation.traindata.Train;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
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
                () -> verifyZeroInteractions(bookingReferenceService),
                () -> verify(trainDataService, times(0)).reserve(anyString(), anyString(), anyList()),
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

    @Test
    void book_all_seats_on_single_coach() {
        String trainId = "trainId";
        String bookingReference = "resa";
        doReturn(new Train(trainId, asList(
                new Coach("A", 10, asList(6, 7, 8, 9, 10)),
                new Coach("B", 10, asList(4, 5, 6, 7, 8, 9, 10))
        ))).when(trainDataService).getTrainById(trainId);
        doReturn(bookingReference)
                .when(bookingReferenceService).getUniqueBookingReference();

        Reservation reservation = service.book(trainId, 6);

        String[] bookedSeats = {"4B", "5B", "6B", "7B", "8B", "9B"};
        assertAll(
                () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                () -> assertThat(reservation)
                        .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                () -> assertThat(reservation.getSeats())
                        .containsExactlyInAnyOrder(bookedSeats)
        );
    }

    @Nested
    class Under70PercentBookingOnTrain {
        String trainId = "trainId";
        String bookingReference = "resa";

        @BeforeEach
        void setupSharedContext() {
            doReturn(new Train(trainId, asList(
                    new Coach("A", 5, emptyList()),
                    new Coach("B", 5, asList(2, 3, 4, 5))
            ))).when(trainDataService).getTrainById(trainId);
        }

        @Test
        void should_book_when_keeps_under_70_percent() {
            doReturn(bookingReference)
                    .when(bookingReferenceService).getUniqueBookingReference();

            Reservation reservation = service.book(trainId, 1);

            String[] bookedSeats = {"2B"};
            assertAll(
                    () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                    () -> assertThat(reservation)
                            .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                    () -> assertThat(reservation.getSeats())
                            .containsExactlyInAnyOrder(bookedSeats)
            );
        }

        @Test
        void should_not_book_when_goes_over_70_percent() {
            Reservation reservation = service.book(trainId, 2);

            assertAll(
                    () -> verifyZeroInteractions(bookingReferenceService),
                    () -> verify(trainDataService, times(0)).reserve(anyString(), anyString(), anyList()),
                    () -> assertThat(reservation)
                            .isEqualToComparingFieldByField(new Reservation(trainId, "", emptyList()))
            );
        }
    }

    @Nested
    class Under70PercentBookingOnCoach {
        String trainId = "trainId";
        String bookingReference = "resa";

        @BeforeEach
        void setupSharedContext() {
            doReturn(new Train(trainId, asList(
                    new Coach("A", 10, asList(7, 8, 9, 10)),
                    new Coach("B", 10, asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            ))).when(trainDataService).getTrainById(trainId);
        }

        @Test
        void should_book_when_keeps_under_70_percent() {
            doReturn(bookingReference)
                    .when(bookingReferenceService).getUniqueBookingReference();

            Reservation reservation = service.book(trainId, 1);

            String[] bookedSeats = {"7A"};
            assertAll(
                    () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                    () -> assertThat(reservation)
                            .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                    () -> assertThat(reservation.getSeats())
                            .containsExactlyInAnyOrder(bookedSeats)
            );
        }

        @Test
        void should_book_next_coach_when_goes_over_70_percent() {
            doReturn(bookingReference)
                    .when(bookingReferenceService).getUniqueBookingReference();

            Reservation reservation = service.book(trainId, 2);

            String[] bookedSeats = {"1B", "2B"};
            assertAll(
                    () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                    () -> assertThat(reservation)
                            .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                    () -> assertThat(reservation.getSeats())
                            .containsExactlyInAnyOrder(bookedSeats)
            );
        }
    }

    @Nested
    class Over70OnCoachButUnder70OnTrain {
        @Test
        void peek_a_last_one() {
            String trainId = "trainId";
            String bookingReference = "resa";
            doReturn(new Train(trainId, asList(
                    new Coach("A", 100, rangeClosed(70, 100).boxed().collect(toList())),
                    new Coach("B", 10, asList(8, 9, 10))
            ))).when(trainDataService).getTrainById(trainId);
            doReturn(bookingReference)
                    .when(bookingReferenceService).getUniqueBookingReference();

            Reservation reservation = service.book(trainId, 1);

            String[] bookedSeats = {"70A"};
            assertAll(
                    () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                    () -> assertThat(reservation)
                            .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                    () -> assertThat(reservation.getSeats())
                            .containsExactlyInAnyOrder(bookedSeats)
            );

        }
    }
}
