package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll();

    Optional<Tag> findOne(Long id);

    Tag create(Tag tag);

    boolean delete(Long id);
}
