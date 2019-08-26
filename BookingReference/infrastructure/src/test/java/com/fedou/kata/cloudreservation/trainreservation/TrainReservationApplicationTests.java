package com.fedou.kata.cloudreservation.trainreservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedou.kata.cloudreservation.trainreservation.bookingreference.BookingReferenceRemote;
import com.fedou.kata.cloudreservation.trainreservation.traindata.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(webEnvironment = MOCK)
public class TrainReservationApplicationTests {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;

    @SpyBean
    protected TrainDataService trainDataService;
    @SpyBean
    protected BookingReferenceRemote bookingReferenceService;

    protected <T> T getResultAsType(MvcResult result, Class<T> valueType) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), valueType);
    }

}
