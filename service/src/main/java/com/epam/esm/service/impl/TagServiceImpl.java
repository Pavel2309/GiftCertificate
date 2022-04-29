package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findOne(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag create(Tag tag) {
        Optional<Tag> currentTag = tagRepository.findByTitle(tag.getTitle());
        if (currentTag.isEmpty()) {
            return tagRepository.create(tag);
        }
        return currentTag.get();
    }

    @Override
    public boolean delete(Long id) {
        return tagRepository.delete(id);
    }
}
