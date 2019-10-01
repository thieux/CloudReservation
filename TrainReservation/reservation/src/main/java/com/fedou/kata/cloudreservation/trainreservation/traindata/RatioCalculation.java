package com.fedou.kata.cloudreservation.trainreservation.traindata;

class RatioCalculation {
    static boolean keepsUnder70PercentAfterBookingWith(int totalSeats, int freeSeats, int seatsToBook) {
        if (totalSeats <= 0) return false; // should not book seats in train without seats (Fret ???) and avoid dividing by zero
        int actualBookedSeats = totalSeats - freeSeats;
        float ratio = (float) (actualBookedSeats + seatsToBook) / (float) totalSeats; // divide with float numbers to avoid dividing integers, then casting in float
        return ratio <= 0.70;
    }
}
