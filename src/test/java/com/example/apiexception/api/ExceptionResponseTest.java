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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ExceptionResponseTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("200 ok: 정상 요청에 대해 응답이 정상적으로 이루어진다.")
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

    @Test
    @DisplayName("400 bad request: json parsing error가 발생할 때 400 응답이 이루어진다.")
    void http_400_wrong_json_format() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        String wrongJson = mapper.writeValueAsString(new RequestDto(1, "testname"));
        wrongJson = "{" + wrongJson; // 잘못된 json 형식

        // when & then
        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/api"));
    }

    @Test
    @DisplayName("400 bad request: required parameter가 누락되었을 때 400 응답이 이루어진다.")
    void http_400_missing_required_parameter() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/api"));
    }

    @Test
    @DisplayName("400 bad request: parameter type mismatch가 발생할 때 400 응답이 이루어진다.")
    void http_400_parameter_type_mismatch() throws Exception {
        // given
        String wrongNumber = "string";

        // when & then
        mockMvc.perform(get("/api")
                        .param("number", wrongNumber))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/api"));
    }


}