package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagServiceImpl tagService;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag getOne(@PathVariable("id") Long id) {
        return tagService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTag(@PathVariable("id") Long id) {
        return tagService.delete(id);
    }
}
