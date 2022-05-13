package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The User service interface.
 */
public interface UserService {
    /**
     * Finds all users.
     *
     * @param pageParameters the page number and size parameters
     * @return a page model object of user
     */
    PagedModel<User> findAll(Map<String, String> pageParameters);

    /**
     * Finds one user with the specified id.
     *
     * @param id a user's id
     * @return an optional object of a user
     */
    Optional<User> findOne(Long id);

    /**
     * Creates a new user.
     *
     * @param userDto a data transfer object of a user
     * @return a created user
     */
    User create(UserDto userDto) throws ServiceException;
}
