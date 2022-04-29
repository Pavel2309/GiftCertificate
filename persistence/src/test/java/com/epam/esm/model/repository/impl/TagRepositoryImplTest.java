package com.epam.esm.model.repository.impl;

import com.epam.esm.config.DataIntegrationConfig;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataIntegrationConfig.class})
@ActiveProfiles("development")
class TagRepositoryImplTest {

    private final TagRepositoryImpl tagRepository;

    private Tag tag;

    @Autowired
    public TagRepositoryImplTest(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setTitle("CreatedTag");
    }

    @Test
    void findAll() {
        List<Tag> tags = tagRepository.findAll();
        assertEquals(4, tags.size());
    }

    @Test
    void findOne() {
        Optional<Tag> tag = tagRepository.findOne(1L);
        Assertions.assertTrue(tag.isPresent());
    }

    @Test
    void create() {
        Tag createdTag = tagRepository.create(tag);
        Assertions.assertEquals(tag.getTitle(), createdTag.getTitle());
    }

    @Test
    void delete() {
        boolean result = tagRepository.delete(4L);
        Assertions.assertTrue(result);
    }

    @Test
    void findByTitle() {
        Optional<Tag> foundTag = tagRepository.findByTitle("tag_1");
        Assertions.assertTrue(foundTag.isPresent());
    }

    @Test
    void findByCertificateId() {
        Set<Tag> foundTags = tagRepository.findByCertificateId(1L);
        Assertions.assertEquals(3, foundTags.size());
    }
}