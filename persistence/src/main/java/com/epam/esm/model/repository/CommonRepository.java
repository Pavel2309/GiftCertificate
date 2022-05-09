package com.epam.esm.model.repository;

import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The CommonRepository interface that describes the CRUD functionality for the specified entity.
 *
 * @param <T> a type parameter in CommonRepository
 * @param <K> a type parameter in CommonRepository
 */
public interface CommonRepository<T, K> {

    /**
     * Finds all entities.
     *
     * @param parameters the page and side parameters
     * @return a list of entities
     */
    PagedModel<T> findAll(Map<String, String> parameters);

    /**
     * Find entity with provided id.
     *
     * @param id an entity's id
     * @return an optional object of a given entity
     */
    Optional<T> findOne(K id);

    /**
     * Creates a new entity.
     *
     * @param t an entity's object
     * @return an object of a created entity
     */
    T create(T t);

    /**
     * Updates an entity.
     *
     * @param t an entity's object
     * @return an object of an updated entity
     */
    T update(T t);

    /**
     * Deletes an entity by the provided id.
     *
     * @param id an entity's id
     * @return whether an entity was deleted successfully
     */
    boolean delete(K id);
}
