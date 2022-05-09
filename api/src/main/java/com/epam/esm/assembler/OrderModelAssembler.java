package com.epam.esm.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.dto.OrderDto;
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
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> {

    private final PaginateLinkCreator paginateLinkCreator;

    public OrderModelAssembler(PaginateLinkCreator paginateLinkCreator) {
        this.paginateLinkCreator = paginateLinkCreator;
    }

    @Override
    public EntityModel<OrderDto> toModel(OrderDto orderDto) {
        return EntityModel.of(orderDto,
                linkTo(methodOn(OrderController.class).getOne(orderDto.getId())).withSelfRel());
    }

    public PagedModel<EntityModel<OrderDto>> toPageModel(PagedModel<OrderDto> orderDtos, Map<String, String> pageParameters) {
        List<EntityModel<OrderDto>> entityModelList = new ArrayList<>();
        orderDtos.forEach(orderDto -> {
            entityModelList.add(toModel(orderDto));
        });
        PagedModel.PageMetadata metadata = orderDtos.getMetadata();
        List<Link> links = paginateLinkCreator.createPaginateLinks(metadata, pageParameters);
        return PagedModel.of(entityModelList, orderDtos.getMetadata(), links);
    }
}
