package com.fedou.kata.cloudreservation.trainreservation.bookingreference;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;

@Repository
public interface BookingReferenceService {
    String getUniqueBookingReference();
}
