package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.util.MultiValueMap;;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("development")
@AutoConfigureMockMvc
@AutoConfigureJson
public class CertificateControllerIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private static Certificate newCertificate;
    private static Certificate certificateForUpdate;
    private static Set<Tag> tagList;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        Tag tagOne = new Tag();
        Tag tagTwo = new Tag();
        tagOne.setTitle("TagOne");
        tagTwo.setTitle("TagTwo");
        tagList = new HashSet<>();
        tagList.add(tagOne);
        tagList.add(tagTwo);

        newCertificate = new Certificate();
        newCertificate.setTitle("Title");
        newCertificate.setDescription("Description");
        newCertificate.setPrice(BigDecimal.TEN);
        newCertificate.setDuration(11);
        newCertificate.setTags(tagList);

        certificateForUpdate = new Certificate();
        certificateForUpdate.setTitle("Updated title");

    }

    @Test
    void getWithParameters() throws Exception {
        MultiValueMap<String, String> parameters1 = new LinkedMultiValueMap<>();
        parameters1.put("tag_title", Collections.singletonList("tag_1"));
        parameters1.put("search_query", Collections.singletonList("Title_1"));
        parameters1.put("sort", Collections.singletonList("title(asc)"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/")
                        .params(parameters1))
                .andExpect(handler().methodName("getWithParameters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", containsInRelativeOrder("Title_1")));
    }

    @ParameterizedTest
    @CsvSource("1, Title_1")
    void getOne(Long id, String title) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title));
    }

    @ParameterizedTest
    @ValueSource(longs = 999L)
    void getOneNotFound(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

    @Test
    void addCertificate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/certificates/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCertificate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(IsNull.notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(longs = 4L)
    void updateCertificate(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/certificates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificateForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.description").value("Description_4"));
    }

    @ParameterizedTest
    @ValueSource(longs = 5L)
    void deleteCertificate(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/certificates/{id}", id))
                .andExpect(status().isOk());
    }
}
