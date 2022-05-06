package com.epam.esm.controller;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The REST API Order controller.
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderModelAssembler assembler;

    public OrderController(OrderServiceImpl orderService, OrderModelAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    /**
     * Gets all orders.
     *
     * @return a list of order data transfer objects
     */
    @GetMapping
    public CollectionModel<EntityModel<OrderDto>> getAll() {
        List<EntityModel<OrderDto>> orders = orderService.findAll().stream()
                .map(assembler::toModel).toList();
        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class)).withSelfRel());
    }

    /**
     * Gets one order with the specified id.
     *
     * @param id an order's id
     * @return an order object
     */
    @GetMapping("/{id}")
    public EntityModel<OrderDto> getOne(@PathVariable("id") Long id) {
        OrderDto orderDto = orderService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return assembler.toModel(orderDto);
    }

    /**
     * Gets user orders with the specified user id
     *
     * @param id a user's id
     * @return an order data transfer object
     */
    @GetMapping("/users/{id}")
    public CollectionModel<EntityModel<OrderDto>> getUserOrders(@PathVariable("id") Long id) {
        List<EntityModel<OrderDto>> orders = orderService.findByUserId(id).stream()
                .map(assembler::toModel).toList();
        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class)).withSelfRel());
    }

    /**
     * Creates a new order
     *
     * @param orderDto an order data transfer object in the JSON format
     * @return an object of a created certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        try {
            return orderService.create(orderDto);
        } catch (ServiceException e) {
            throw new ResourceNotFoundException(orderDto.getId());
        }
    }
}
