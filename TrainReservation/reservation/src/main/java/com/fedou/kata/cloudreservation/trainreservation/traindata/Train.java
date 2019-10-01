package com.fedou.kata.cloudreservation.trainreservation.traindata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static com.fedou.kata.cloudreservation.trainreservation.traindata.RatioCalculation.keepsUnder70PercentAfterBookingWith;
import static java.util.Collections.emptyList;

public class Train {
    private static Logger log = LoggerFactory.getLogger(Train.class);
    private final String trainId;
    private int totalFreeSeats;
    private int totalSeats;
    private List<Coach> coaches = new LinkedList<>();

    public Train(String trainId, List<Coach> coaches) {
        this.trainId = trainId;
        this.coaches.addAll(coaches);
        coaches.forEach(coach -> {
            totalSeats += coach.getTotalSeats();
            totalFreeSeats += coach.getTotalFreeSeats();
        });
    }

    public List<String> findSeatsForBooking(int numberOfSeats) {
        Coach.BookingOption result = Coach.emptyOption;
        if (keepsUnder70PercentAfterBookingWith(totalSeats, totalFreeSeats, numberOfSeats)) {
            for (Coach current : coaches) {
                Coach.BookingOption option = current.findSeatsForBooking(numberOfSeats);
                if (option.seatsFound()) {
                    if (option.respectFreeSeatsRatio) {
                        result = option;
                        break;
                    } else {
                        if (result == Coach.emptyOption) {
                            result = option;
                        }
                    }
                }
            }
        }
        logResult(numberOfSeats, result);
        return result.seats;
    }

    private void logResult(int numberOfSeats, Coach.BookingOption seats) {
        log.debug("Booking {} seats for train {} gives {}.", numberOfSeats, this, seats);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Train{");
        sb.append("trainId='").append(trainId).append('\'');
        sb.append(", totalSeats=").append(totalSeats);
        sb.append(", totalFreeSeats=").append(totalFreeSeats);
        sb.append(", ratio=").append(((float) (totalSeats - totalFreeSeats) / (float) totalSeats));
        sb.append(", coaches=").append(coaches);
        sb.append('}');
        return sb.toString();
    }
}
