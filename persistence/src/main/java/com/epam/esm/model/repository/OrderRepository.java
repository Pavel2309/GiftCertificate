package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;

import java.util.List;

/**
 * The OrderRepository interface describes data access functionality for the Order entity.
 */
public interface OrderRepository extends CommonRepository<Order, Long> {

    /**
     * Finds all orders with the specified user id.
     *
     * @param id a user's id
     * @return a list of order objects.
     */
    List<Order> findOrdersByUserId(Long id);

    /**
     * Establishes a link between a provided order and its corresponded certificates
     *
     * @param order an order object
     * @return whether an order and its corresponded tags are linked successfully
     */
    boolean linkOrderWithCertificates(Order order);

    /**
     * Removes a link between a provided order and its corresponded certificates
     *
     * @param id an order's id
     * @return whether order's certificates were successfully removed from the order
     */
    boolean unlinkOrderWithCertificates(Long id);
}
