package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Gets user orders with the specified user id
     *
     * @param id a user's id
     * @return an order data transfer object
     */
    @GetMapping("/users/{id}")
    public List<OrderDto> getUserOrders(@PathVariable("id") Long id) {
        return orderService.findByUserId(id);
    }
}
