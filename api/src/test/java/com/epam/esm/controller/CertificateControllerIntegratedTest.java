package com.epam.esm.controller;

import com.epam.esm.config.DataInMemoryConfig;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataInMemoryConfig.class})
@ActiveProfiles("development")
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificateControllerIntegratedTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
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

    @Test
    void addCertificate() throws Exception {
        String newCertificateJson = """
                {
                    "title": "Test three",
                    "description": "description",
                    "price": 321,
                    "duration": 11,
                    "tags": [{"title": "tag3"}, {"title":"tag1"}]
                }""";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/certificates/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCertificateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(IsNull.notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(longs = 4L)
    void updateCertificate(Long id) throws Exception {
        String updatedCertificate = """
                {
                    "title": "Updated title"
                }
                 """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/certificates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCertificate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated title"));

    }

    @ParameterizedTest
    @ValueSource(longs = 5L)
    void deleteCertificate(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/certificates/{id}", id))
                .andExpect(status().isOk());
    }
}
