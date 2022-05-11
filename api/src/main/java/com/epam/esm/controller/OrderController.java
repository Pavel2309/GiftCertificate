package com.epam.esm.controller;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * @param parameters a page and size parameters for pagination
     * @return a list of order data transfer objects
     */
    @GetMapping
    public PagedModel<EntityModel<OrderDto>> getAll(@RequestParam Map<String, String> parameters) {
        PagedModel<OrderDto> orderDtos = orderService.findAll(parameters);
        return assembler.toPageModel(orderDtos, parameters);
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
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        return assembler.toModel(orderDto);
    }

    /**
     * Gets user orders with the specified user id
     *
     * @param id a user's id
     * @param parameters a page and size parameters for pagination
     * @return an order data transfer object
     */
    @GetMapping("/users/{id}")
    public PagedModel<EntityModel<OrderDto>> getUserOrders(@PathVariable("id") Long id,
                                                                @RequestParam Map<String, String> parameters) {
        PagedModel<OrderDto> orderDtos = orderService.findByUserId(id, parameters);
        return assembler.toPageModel(orderDtos, parameters);
    }

    /**
     * Creates a new order
     *
     * @param orderDto an order data transfer object in the JSON format
     * @return a data transfer object of a created order
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        try {
            return orderService.create(orderDto);
        } catch (ServiceException e) {
            throw new ResourceNotFoundException(orderDto.getId().toString());
        }
    }
}
