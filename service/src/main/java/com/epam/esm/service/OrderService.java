package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Order;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Order service interface.
 */
public interface OrderService {
    /**
     * Finds all orders.
     *
     * @param pageParameters the page number and size parameters
     * @return a page model object of order data transfer object
     */
    PagedModel<OrderDto> findAll(Map<String, String> pageParameters);

    /**
     * Finds all orders with the specified user id.
     *
     * @param id a user's id
     * @param pageParameters the page number and size parameters
     * @return a page model object of order data transfer object
     */
    PagedModel<OrderDto> findByUserId(Long id, Map<String, String> pageParameters);

    /**
     * Finds one order with the specified id.
     *
     * @param id an order's id
     * @return an optional of an order data transfer object
     */
    Optional<OrderDto> findOne(Long id);

    /**
     * Creates a new order.
     *
     * @param orderDto an order data transfer object
     * @return a created order
     */
    OrderDto create(OrderDto orderDto) throws ServiceException;

    /**
     * Deletes an order with the specified id.
     *
     * @param id an order's id
     * @return whether an order was deleted successfully
     */
    boolean delete(Long id);
}
