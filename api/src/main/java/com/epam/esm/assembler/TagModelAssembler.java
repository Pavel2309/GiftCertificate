package com.epam.esm.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.entity.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {
    @Override
    public EntityModel<Tag> toModel(Tag tag) {
        return EntityModel.of(tag,
                linkTo(methodOn(TagController.class).getOne(tag.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getAll()).withRel("tags"));
    }
}
