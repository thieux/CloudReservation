package com.fedou.kata.cloudreservation.trainreservation.rest;

import com.fedou.kata.cloudreservation.trainreservation.TrainReservationApplicationMonolithTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
class TicketOfficeControllerMonolithTest extends TrainReservationApplicationMonolithTests {
    @Value("${reservationUrl}")
    private String reservationUrl;

    @Test
    public void makeReservation() throws Exception {
//        doReturn("something").when(bookingReferenceService).getUniqueBookingReference();
//        TrainData trainData = TrainDataBuilder.theAcceptanceTrain();
//        doReturn(trainData).when(trainDataService).getTrainData(eq("express_2000"));
        String url = reservationUrl + "/reservation/makeReservation";
        System.err.println("url = " + url);
        MvcResult result = mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ReservationRequestDTO("express_2000", 2))))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(getResultAsType(result, ReservationDTO.class))
                .isEqualToComparingFieldByField(
                        new ReservationDTO(
                                "express_2000",
                                "something",
                                Arrays.asList("1A", "2A")));
        ;
    }

}