package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;

import java.util.Optional;

/**
 * The UserRepository interface describes data access functionality for the User entity.
 */
public interface UserRepository extends CommonRepository<User, Long> {

    /**
     * Finds user by specified email.
     *
     * @param email a user's email
     * @return an object of a user
     */
    Optional<User> findByEmail(String email);

}
