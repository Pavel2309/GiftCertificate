package com.epam.esm.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.util.PaginateLinkCreator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {

    private final PaginateLinkCreator paginateLinkCreator;

    public TagModelAssembler(PaginateLinkCreator paginateLinkCreator) {
        this.paginateLinkCreator = paginateLinkCreator;
    }

    @Override
    public EntityModel<Tag> toModel(Tag tag) {
        return EntityModel.of(tag,
                linkTo(methodOn(TagController.class).getOne(tag.getId())).withSelfRel());
    }

    public PagedModel<EntityModel<Tag>> toPageModel(PagedModel<Tag> tags, Map<String, String> pageParameters) {
        List<EntityModel<Tag>> entityModelList = new ArrayList<>();
        tags.forEach(tag -> {
            entityModelList.add(toModel(tag));
        });
        PagedModel.PageMetadata metadata = tags.getMetadata();
        List<Link> links = paginateLinkCreator.createPaginateLinks(metadata, pageParameters);
        return PagedModel.of(entityModelList, tags.getMetadata(), links);
    }
}
