package com.example.apiexception.api;

import com.example.apiexception.api.dto.RequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    @DisplayName("404 not found: 존재하지 않는 api 요청에 대해 404 응답이 이루어진다.")
    void http_404_not_found() throws Exception {
        mockMvc.perform(get("/wrong/url"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 api입니다."))
                .andExpect(jsonPath("$.path").value("/wrong/url"));
    }
}