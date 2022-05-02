package com.epam.esm.controller;

import com.epam.esm.config.DataInMemoryConfig;
import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataInMemoryConfig.class})
@ActiveProfiles("development")
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagControllerIntegratedTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Tag newTag;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    void setUp() {
        newTag = new Tag();
        newTag.setTitle("NewTag");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/"))
                .andExpect(handler().handlerType(TagController.class))
                .andExpect(handler().methodName("getAll"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource("1, tag_1")
    void getOne(Long id, String title) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title));
    }

    @Test
    void addTag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tags/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTag)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
                .andExpect(jsonPath("$.title").value(newTag.getTitle()));
    }

    @ParameterizedTest
    @ValueSource(longs = 5L)
    void deleteTag(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tags/{id}", id))
                .andExpect(status().isOk());
    }
}
