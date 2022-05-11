package com.epam.esm.controller;

import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The REST API Tag controller.
 */
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagServiceImpl tagService;
    private final TagModelAssembler assembler;

    public TagController(TagServiceImpl tagService, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    /**
     * Gets all tags.
     *
     * @param parameters a page and size parameters for pagination
     * @return a list of tag objects
     */
    @GetMapping
    public PagedModel<EntityModel<Tag>> getAll(@RequestParam Map<String, String> parameters) {
        PagedModel<Tag> certificates = tagService.findAll(parameters);
        return assembler.toPageModel(certificates, parameters);
    }

    /**
     * Gets one tag with the specified id.
     *
     * @param id a tag's id
     * @return a tag object
     */
    @GetMapping("/{id}")
    public EntityModel<Tag> getOne(@PathVariable("id") Long id) {
        Tag tag = tagService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        return assembler.toModel(tag);
    }

    /**
     * Gets the most frequently used tag (tags) of a user (users) who purchased the most certificates (total amount of money spent).
     *
     * @return a list of tag objects
     */
    @GetMapping("/popular")
    public CollectionModel<EntityModel<Tag>> getPopularTagsOfUsersWhoSpentMost() {
        List<EntityModel<Tag>> tags = tagService.findMostPopularTagsOfUsersWhoSpentMost().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags,
                linkTo(methodOn(TagController.class).getPopularTagsOfUsersWhoSpentMost()).withSelfRel());
    }

    /**
     * Creates a new tag
     *
     * @param tag a tag object in the JSON format
     * @return an object of a created tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addTag(@RequestBody @Valid Tag tag) {
        return tagService.create(tag);
    }

    /**
     * Deletes a tag with a specified id.
     *
     * @param id a tag's id
     * @return whether a tag was deleted successfully
     */
    @DeleteMapping("/{id}")
    public boolean deleteTag(@PathVariable("id") Long id) {
        return tagService.delete(id);
    }
}
