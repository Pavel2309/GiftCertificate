package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The Tag service interface.
 */
public interface TagService {
    /**
     * Finds all tags.
     *
     * @return a list of tag objects
     */
    List<Tag> findAll();

    /**
     * Finds one tag with the specified id.
     *
     * @param id a tag's id
     * @return an optional object of a tag
     */
    Optional<Tag> findOne(Long id);

    /**
     * Creates a new tag.
     *
     * @param tag a tag object
     * @return a created tag
     */
    Tag create(Tag tag);

    /**
     * Deletes a tag with the specified id.
     *
     * @param id a tag's id
     * @return whether a tag was deleted successfully
     */
    boolean delete(Long id);
}
