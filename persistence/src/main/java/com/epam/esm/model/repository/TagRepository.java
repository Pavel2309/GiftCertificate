package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The TagRepository interface describes data access functionality for the Tag entity.
 */
public interface TagRepository extends CommonRepository<Tag, Long> {
    /**
     *  Finds a tag with the specified title.
     *
     * @param title a tag's title
     * @return an optional object of a tag
     */
    Optional<Tag> findByTitle(String title);

    /**
     *  Finds all tags that belong to the specified certificate by certificate's id.
     *
     * @param id a certificate's id
     * @return a list of tags
     */
    List<Tag> findByCertificateId(Long id);

    /**
     * Finds the most frequently used tag (tags) of a user (users) who purchased the most certificates (total amount of money spent).
     *
     * @return a list of tag objects
     */
    List<Tag> findMostPopularTagsOfUsersWhoSpentMost();
}
