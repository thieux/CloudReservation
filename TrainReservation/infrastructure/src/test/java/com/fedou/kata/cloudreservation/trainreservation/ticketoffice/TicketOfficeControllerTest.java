package com.fedou.kata.cloudreservation.trainreservation.ticketoffice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fedou.kata.cloudreservation.trainreservation.TrainReservationApplicationTests;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.fedou.kata.cloudreservation.traindata.TrainDataBuilder.theAcceptanceTrain;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Arrays.asList;
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
        List<String> bookedSeats = asList("1A","2A");

        givenTheTrainDataServerWillProvide(trainId, trainData);
        givenTheBookingReferenceServerWillProvide(bookingId);
        ReservationRequestDTO bookingRequest = new ReservationRequestDTO(trainId, 2);
        givenTheTrainDataServerWillBeCalledForAReservation();

        ReservationDTO actualReservation = whenMakeReservationFor(bookingRequest);

        ReservationDTO expectedReservation = new ReservationDTO(
                trainId,
                bookingId,
                bookedSeats);

        thenTheTrainDataServerReceives(expectedReservation);
        assertThat(actualReservation)
                .isEqualToComparingFieldByField(expectedReservation);
    }

    private void givenTheBookingReferenceServerWillProvide(String bookingId) {
        givenThat(get("/booking_reference").willReturn(ok(bookingId)));
    }

    private void givenTheTrainDataServerWillProvide(String trainId, TrainData trainData) throws JsonProcessingException {
        givenThat(get("/data_for_train/" + trainId)
                .willReturn(aResponse()
                        .withHeader("content-type", "application/json")
                        .withBody(getRequestObjectAsJson(trainData))));
    }

    private void givenTheTrainDataServerWillBeCalledForAReservation() {
        givenThat(post("/reserve").willReturn(ok()));
    }

    private ReservationDTO whenMakeReservationFor(ReservationRequestDTO bookingRequest) throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/reservation/makeReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestObjectAsJson(bookingRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return getResponseContentAs(result, ReservationDTO.class);
    }

    private void thenTheTrainDataServerReceives(ReservationDTO reservation) throws JsonProcessingException {
        verify(1, postRequestedFor(urlEqualTo("/reserve"))
                .withRequestBodyPart(aMultipart()
                        .withName("trainId")
                        .withBody(containing(reservation.getTrain_id())).build())
                .withRequestBodyPart(aMultipart()
                        .withName("seats")
                        .withBody(containing(getRequestObjectAsJson(reservation.getSeats()))).build())
                .withRequestBodyPart(aMultipart()
                        .withName("bookingId")
                        .withBody(containing(reservation.getBooking_reference())).build()));
    }

}
