package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The User service interface.
 */
public interface UserService {
    /**
     * Finds all users.
     *
     * @return a list of user objects
     */
    List<User> findAll();

    /**
     * Finds one user with the specified id.
     *
     * @param id a user's id
     * @return an optional object of a user
     */
    Optional<User> findOne(Long id);
}
