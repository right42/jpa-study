package me.right42.jpastudy.shop.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void 회원가입_v1() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", "hello");

        mockMvc.perform(
                post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(params))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
        ;

    }

    private String toJson(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(value);
    }

}