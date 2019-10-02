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
        Reservation reservation = service.book("trainId", 0);

        assertAll(
                () -> verifyZeroInteractions(bookingReferenceService),
                () -> verifyZeroInteractions(trainDataService),
                () -> assertThat(reservation)
                        .isEqualToComparingFieldByField(new Reservation(null, null, emptyList()))
        );
    }

    @Test
    void should_book_available_seats() {
        String trainId = "trainId";
        String bookingReference = "resa";
        doReturn(new Train(trainId, singletonList(new Coach("A", 10, asList(3, 5, 6, 7, 8, 9, 10)))))
                .when(trainDataService).getTrainById(trainId);
        doReturn(bookingReference)
                .when(bookingReferenceService).getUniqueBookingReference();

        Reservation reservation = service.book(trainId, 1);

        thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"3A"});
    }

    @Test
    void should_fail_booking_when_no_seat_found() {
        String trainId = "trainId";

        doReturn(new Train(trainId, singletonList(new Coach("A", 1, emptyList()))))
                .when(trainDataService).getTrainById(trainId);

        Reservation reservation = service.book(trainId, 1);

        thenNoBookingDoneAndEmptyBookingReturned(trainId, reservation);
    }

    @Test
    void should_book_sibling_seats() {
        String trainId = "trainId";
        String bookingReference = "resa";

        doReturn(new Train(trainId, singletonList(new Coach("A", 5, asList(1, 3, 4, 5)))))
                .when(trainDataService).getTrainById(trainId);
        doReturn(bookingReference)
                .when(bookingReferenceService).getUniqueBookingReference();

        Reservation reservation = service.book(trainId, 2);

        thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"1A", "3A"});
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

        thenBookingIsDoneAndReturned(trainId, bookingReference, reservation,
                new String[]{"4B", "5B", "6B", "7B", "8B", "9B"});
    }

    private void thenBookingIsDoneAndReturned(String trainId, String bookingReference, Reservation reservation, String[] bookedSeats) {
        assertAll(
                () -> verify(trainDataService).reserve(trainId, bookingReference, asList(bookedSeats)),
                () -> assertThat(reservation)
                        .isEqualToIgnoringNullFields(new Reservation(trainId, bookingReference, null)),
                () -> assertThat(reservation.getSeats())
                        .containsExactlyInAnyOrder(bookedSeats)
        );
    }

    private void thenNoBookingDoneAndEmptyBookingReturned(String trainId, Reservation reservation) {
        assertAll(
                () -> verifyZeroInteractions(bookingReferenceService),
                () -> verify(trainDataService, times(0))
                        .reserve(anyString(), anyString(), anyList()),
                () -> assertThat(reservation)
                        .isEqualToComparingFieldByField(new Reservation(trainId, "", emptyList()))
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

            thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"2B"});
        }

        @Test
        void should_not_book_when_goes_over_70_percent() {
            Reservation reservation = service.book(trainId, 2);

            thenNoBookingDoneAndEmptyBookingReturned(trainId, reservation);
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

            thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"7A"});
        }

        @Test
        void should_book_next_coach_when_goes_over_70_percent() {
            doReturn(bookingReference)
                    .when(bookingReferenceService).getUniqueBookingReference();

            Reservation reservation = service.book(trainId, 2);

            thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"1B", "2B"});
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

            thenBookingIsDoneAndReturned(trainId, bookingReference, reservation, new String[]{"70A"});

        }

        @Test
        void peek_again_and_fail() {
            String trainId = "trainId";
            doReturn(new Train(trainId, asList(
                    new Coach("A", 100, rangeClosed(71, 100).boxed().collect(toList())),
                    new Coach("B", 10, asList(8, 9, 10))
            ))).when(trainDataService).getTrainById(trainId);

            Reservation reservation = service.book(trainId, 1);

            thenNoBookingDoneAndEmptyBookingReturned(trainId, reservation);

        }
    }
}
