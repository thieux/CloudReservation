package com.fedou.kata.cloudreservation.trainreservation.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedou.kata.cloudreservation.traindata.TrainDataBuilder;
import com.fedou.kata.cloudreservation.trainreservation.TrainReservationApplicationTests;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
class TicketOfficeControllerTest extends TrainReservationApplicationTests {

    @Test
    public void makeReservation() throws Exception {
        doReturn("something").when(bookingReferenceService).getUniqueBookingReference();
        TrainData trainData = TrainDataBuilder.theAcceptanceTrain();
        doReturn(trainData).when(trainDataService).getTrainData(eq("express_2000"));

        MvcResult result = mvc.perform(
                post(
                        "/reservation/makeReservation")
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