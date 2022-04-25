package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {



    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Optional<Tag> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Tag create(Tag tag) {
        return null;
    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }

    @Override
    public Tag delete(Tag tag) {
        return null;
    }
}
