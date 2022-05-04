package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The REST API Order controller.
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    /**
     * Gets all orders.
     *
     * @return a list of order data transfer objects
     */
    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.findAll();
    }

    /**
     * Gets one order with the specified id.
     *
     * @param id an order's id
     * @return an order object
     */
    @GetMapping("/{id}")
    public OrderDto getOne(@PathVariable("id") Long id) {
        return orderService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Creates a new order
     *
     * @param order an order object in the JSON format
     * @return an object of a created order
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody @Valid Order order) {
        return orderService.create(order);
    }
}
