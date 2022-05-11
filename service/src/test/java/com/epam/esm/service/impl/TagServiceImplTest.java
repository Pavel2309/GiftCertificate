package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private static TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl service;

    private Tag tagOne;
    private Tag tagTwo;
    private List<Tag> tagList;
    private PagedModel<Tag> pagedModel;

    @BeforeEach
    void setUp() {
        tagOne = new Tag();
        tagTwo = new Tag();
        tagOne.setId(1L);
        tagTwo.setId(2L);
        tagList = new ArrayList<>();
        tagList.add(tagOne);
        tagList.add(tagTwo);

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(10, 1, 2, 1);
        pagedModel = PagedModel.of(tagList, metadata);
    }

    @AfterEach
    void tearDown() {
        tagOne = null;
        tagTwo = null;
        tagList = null;
    }

    @Test
    void findAll() {
        Mockito.when(tagRepository.findAll(anyMap())).thenReturn(pagedModel);
        List<Tag> foundTags = service.findAll(new HashMap<>()).getContent().stream().toList();
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