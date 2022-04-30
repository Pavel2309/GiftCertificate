package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The REST API Tag controller.
 */
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagServiceImpl tagService;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    /**
     * Gets all tags.
     *
     * @return a list of tag objects
     */
    @GetMapping
    public List<Tag> getAll() {
        return tagService.findAll();
    }

    /**
     * Gets one tag with the specified id.
     *
     * @param id a tag's id
     * @return a tag object
     */
    @GetMapping("/{id}")
    public Tag getOne(@PathVariable("id") Long id) {
        return tagService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
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
