package com.epam.esm.controller;

import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @return a list of tag objects
     */
    @GetMapping
    public CollectionModel<EntityModel<Tag>> getAll() {
        List<EntityModel<Tag>> tags = tagService.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(tags, linkTo(methodOn(TagController.class).getAll()).withSelfRel());
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
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return assembler.toModel(tag);
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
