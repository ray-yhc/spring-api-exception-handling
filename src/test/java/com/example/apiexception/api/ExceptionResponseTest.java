package com.example.apiexception.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ExceptionResponseTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("200 ok: 정상인 요청 테스트")
    void http_ok() throws Exception {
        // given
        int number = 1;
        // when & then
        mockMvc.perform(get("/api")
                        .param("number", number + ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Your number is " + number));
    }

}