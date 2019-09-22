package com.fedou.kata.cloudreservation.trainreservation.rest;

import com.fedou.kata.cloudreservation.trainreservation.TrainReservationApplicationTests;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.fedou.kata.cloudreservation.traindata.TrainDataBuilder.theAcceptanceTrain;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class TicketOfficeControllerTest extends TrainReservationApplicationTests {

    @Test
    public void makeReservation() throws Exception {
        String trainId = "express_2000";
        TrainData trainData = theAcceptanceTrain();
        String bookingId = "something";

        givenThat(get("/booking_reference")
                .willReturn(ok(bookingId)));

        givenThat(get("/data_for_train/" + trainId)
                .willReturn(aResponse()
                        .withHeader("content-type", "application/json")
                        .withBody(getRequestObjectAsJson(trainData))));

        givenThat(post("/reserve")
                .withMultipartRequestBody(aMultipart()
                        .withName("trainId")
                        .withBody(containing(trainId)))
                .withMultipartRequestBody(aMultipart()
                        .withName("seats")
                        .withBody(containing("[\"1A\",\"2A\"]")))
                .withMultipartRequestBody(aMultipart()
                        .withName("bookingId")
                        .withBody(containing(bookingId))));

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/reservation/makeReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestObjectAsJson(new ReservationRequestDTO(trainId, 2))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(getResponseContentAs(result, ReservationDTO.class))
                .isEqualToComparingFieldByField(
                        new ReservationDTO(
                                trainId,
                                bookingId,
                                Arrays.asList("1A", "2A")));
        ;
    }

}