package com.fedou.kata.cloudreservation.traindata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(webEnvironment = MOCK)
public class TrainDataApplicationTests {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;

    protected <T> T getResultAsType(MvcResult result, Class<T> valueType) throws java.io.IOException {
        String string = result.getResponse().getContentAsString();
        return parseTo(string, valueType);
    }

    protected <T> T parseTo(String string, Class<T> valueType) throws java.io.IOException {
        return mapper.readValue(string, valueType);
    }

}
