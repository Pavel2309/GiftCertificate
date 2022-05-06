package com.epam.esm.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.dto.OrderDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> {
    @Override
    public EntityModel<OrderDto> toModel(OrderDto orderDto) {
        return EntityModel.of(orderDto,
                linkTo(methodOn(OrderController.class).getOne(orderDto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getUserOrders(orderDto.getUserId())).withRel("all orders of the user with id " + orderDto.getUserId()));
    }
}
