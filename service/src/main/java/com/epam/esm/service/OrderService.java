package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * The Order service interface.
 */
public interface OrderService {
    /**
     * Finds all orders.
     *
     * @return a list of order objects
     */
    List<Order> findAll();

    /**
     * Finds all orders with the specified user id.
     *
     * @param id a user's id
     * @return a list of order objects
     */
    List<Order> findByUserId(Long id);

    /**
     * Finds one order with the specified id.
     *
     * @param id an order's id
     * @return an optional object of an order
     */
    Optional<Order> findOne(Long id);

    /**
     * Creates a new order.
     *
     * @param order an order object
     * @return a created order
     */
    Order create(Order order);

    /**
     * Deletes an order with the specified id.
     *
     * @param id an order's id
     * @return whether an order was deleted successfully
     */
    boolean delete(Long id);
}
