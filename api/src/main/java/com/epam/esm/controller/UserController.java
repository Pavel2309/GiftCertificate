package com.epam.esm.controller;

import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The REST API User controller.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;
    private final UserModelAssembler assembler;

    public UserController(UserServiceImpl userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    /**
     * Gets all users.
     *
     * @param parameters a page and size parameters for pagination
     * @return a list of user objects
     */
    @GetMapping
    public PagedModel<EntityModel<User>> getAll(Map<String, String> parameters) {
        PagedModel<User> users = userService.findAll(parameters);
        return assembler.toPageModel(users, parameters);
    }

    /**
     * Gets one user with the specified id.
     *
     * @param id a user's id
     * @return a user object
     */
    @GetMapping("/{id}")
    public EntityModel<User> getOne(@PathVariable("id") Long id) {
        User user = userService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        return assembler.toModel(user);
    }
}
