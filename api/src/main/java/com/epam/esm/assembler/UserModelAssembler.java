package com.epam.esm.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.entity.User;
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
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    private final PaginateLinkCreator paginateLinkCreator;

    public UserModelAssembler(PaginateLinkCreator paginateLinkCreator) {
        this.paginateLinkCreator = paginateLinkCreator;
    }

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel());
    }

    public PagedModel<EntityModel<User>> toPageModel(PagedModel<User> users, Map<String, String> pageParameters) {
        List<EntityModel<User>> entityModelList = new ArrayList<>();
        users.forEach(user -> {
            entityModelList.add(toModel(user));
        });
        PagedModel.PageMetadata metadata = users.getMetadata();
        List<Link> links = paginateLinkCreator.createPaginateLinks(metadata, pageParameters);
        return PagedModel.of(entityModelList, users.getMetadata(), links);
    }
}
