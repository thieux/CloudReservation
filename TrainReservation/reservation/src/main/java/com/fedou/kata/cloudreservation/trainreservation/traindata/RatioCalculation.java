package com.fedou.kata.cloudreservation.trainreservation.traindata;

class RatioCalculation {
    static boolean isUnder70PercentWhenBookingOf(int totalSeats, int freeSeats, int seatsToBook) {
        if (totalSeats <= 0) return false; // should not book seats in train without seats (Fret ???)
        int actualBookedSeats = totalSeats - freeSeats;
        float ratio = (float) (actualBookedSeats + seatsToBook) / (float) totalSeats; // divide with an convert in foat afterwards is too late
        return ratio <= 0.70;
    }
}
