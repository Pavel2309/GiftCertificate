package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends CommonRepository<Tag, Long> {

    Optional<Tag> findByTitle(String title);

    Set<Tag> findByCertificateId(Long id);

}
