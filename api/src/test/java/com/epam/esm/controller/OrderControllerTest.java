package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("development")
@AutoConfigureMockMvc
@AutoConfigureJson
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        MultiValueMap<String, String> pageParameters = new LinkedMultiValueMap<>();
        pageParameters.put("page", Collections.singletonList("1"));
        pageParameters.put("size", Collections.singletonList("10"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/")
                        .params(pageParameters))
                .andExpect(handler().methodName("getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orderDtoList[*].id").value(IsNull.notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void getOne(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void getUserOrders(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orderDtoList[*].userId").isArray());
    }

    @Test
    void createOrder() throws Exception {
        OrderDto newOrderDto = new OrderDto();
        List<Long> certificateIds = new ArrayList<>();
        certificateIds.add(1L);
        newOrderDto.setUserId(1L);
        newOrderDto.setCertificatesId(certificateIds);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(IsNull.notNullValue()));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}