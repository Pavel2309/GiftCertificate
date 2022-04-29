package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TagServiceImplTest {

    @Mock
    private static TagRepository tagRepository;

    @Autowired
    @InjectMocks
    private TagServiceImpl service;

    private Tag tagOne;
    private Tag tagTwo;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagOne = new Tag();
        tagTwo = new Tag();
        tagOne.setId(1L);
        tagTwo.setId(2L);
        tagList = new ArrayList<>();
        tagList.add(tagOne);
        tagList.add(tagTwo);
    }

    @AfterEach
    void tearDown() {
        tagOne = null;
        tagTwo = null;
        tagList = null;
    }

    @Test
    void findAll() {
        Mockito.when(tagRepository.findAll()).thenReturn(tagList);
        List<Tag> foundTags = service.findAll();
        Assertions.assertEquals(tagList, foundTags);
    }

    @Test
    void findOne() {
        Mockito.when(tagRepository.findOne(1L)).thenReturn(Optional.ofNullable(tagOne));
        Tag foundTag = service.findOne(1L).get();
        Assertions.assertEquals(tagOne, foundTag);

    }

    @Test
    void create() {
        Mockito.when(tagRepository.create(any())).thenReturn(tagOne);
        Tag createdTag = service.create(tagOne);
        Assertions.assertEquals(tagOne, createdTag);
    }

    @Test
    void delete() {
        Mockito.when(tagRepository.delete(1L)).thenReturn(true);
        boolean result = service.delete(1L);
        Assertions.assertTrue(result);
    }
}