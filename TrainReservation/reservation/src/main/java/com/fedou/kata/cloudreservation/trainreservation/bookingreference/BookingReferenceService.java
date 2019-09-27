package com.fedou.kata.cloudreservation.trainreservation.bookingreference;

import org.springframework.stereotype.Repository;

@Repository
public interface BookingReferenceService {
    String getUniqueBookingReference();
}
