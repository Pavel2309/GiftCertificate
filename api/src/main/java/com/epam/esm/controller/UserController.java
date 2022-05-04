package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The REST API User controller.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Gets all users.
     *
     * @return a list of user objects
     */
    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    /**
     * Gets one user with the specified id.
     *
     * @param id a user's id
     * @return a user object
     */
    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@PathVariable("id") Long id, @RequestBody OrderDto orderDto) {
        return null;
    }
}
