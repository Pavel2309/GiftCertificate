package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CommonRepository<Tag, Long> {

    Optional<Tag> findByTitle(String title);

    List<Tag> findByCertificateId(Long id);

}
