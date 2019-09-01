package com.fedou.kata.cloudreservation.bookingreference;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingReferenceServerTest extends BookingReferenceApplicationTests {

    @Test
    void provideUniqueUniversalId() throws Exception {
        assertThat(getUUID())
                .isNotBlank()
                .isNotEqualToIgnoringCase(getUUID());
    }

    private String getUUID() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/booking_reference"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return mvcResult.getResponse().getContentAsString();
    }
}