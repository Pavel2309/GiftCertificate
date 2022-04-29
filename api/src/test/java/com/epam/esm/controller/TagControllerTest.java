package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Mock
    private TagServiceImpl tagService;
    private Tag tag;
    private List<Tag> tagList;

    @Autowired
    @InjectMocks
    private TagController tagController;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1L);
        tagList = new ArrayList<>();
        tagList.add(tag);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @AfterEach
    void tearDown() {
        tag = null;
    }

    @Test
    void getAll() throws Exception {
        when(tagService.findAll()).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andDo(MockMvcResultHandlers.print());
        verify(tagService).findAll();
        verify(tagService, times(1)).findAll();
    }

    @Test
    void getOne() throws Exception {
        when(tagService.findOne(tag.getId())).thenReturn(Optional.ofNullable(tag));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addTag() throws Exception {
        when(tagService.create(any())).thenReturn(tag);
        mockMvc.perform(post("/api/v1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andExpect(status().isCreated());
        verify(tagService, times(1)).create(any());
    }

    @Test
    void deleteTag() throws Exception {
        when(tagService.delete(tag.getId())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}