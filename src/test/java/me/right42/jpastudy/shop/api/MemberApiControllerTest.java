package me.right42.jpastudy.shop.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.right42.jpastudy.shop.domain.Member;
import me.right42.jpastudy.shop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

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

    @Test
    @Transactional
    void 회원수정_v2() throws Exception {
        Member member = new Member();
        member.setName("hello");

        memberRepositoryOld.save(member);
        Long id = member.getId();

        Map<String, String> params = new HashMap<>();
        params.put("name", "new-hello");
        params.put("id", id.toString());

        mockMvc.perform(
                    put("/api/v2/members/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(params))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(params.get("name")))
            ;

    }

    private String toJson(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(value);
    }

}