package com.epam.esm.controller;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("development")
@AutoConfigureMockMvc
@AutoConfigureJson
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        MultiValueMap<String, String> pageParameters = new LinkedMultiValueMap<>();
        pageParameters.put("page", Collections.singletonList("1"));
        pageParameters.put("size", Collections.singletonList("10"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/")
                        .params(pageParameters))
                .andExpect(handler().methodName("getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userList[*].id").value(IsNull.notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void getOne(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }
}